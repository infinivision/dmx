package com.dmx.api.controller.meta;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.analysis.TCustomerRepository;
import com.dmx.api.dao.analysis.TTagRepository;
import com.dmx.api.dao.meta.TSegmentMetaRepository;
import com.dmx.api.entity.analysis.TCustomerEntity;
import com.dmx.api.entity.meta.TSegmentMetaEntity;
import com.dmx.api.service.bean.SegmentRuleBean;
import com.dmx.api.service.bean.SegmentRulesBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/segment")

public class TSegmentMetaController {
    @Autowired
    TSegmentMetaRepository tSegmentMetaRepository;

    @Autowired
    TTagRepository tTagRepository;

    @Autowired
    TCustomerRepository tCustomerRepository;

    @GetMapping("/{segment_id}")
    public GetMessageResponse<TSegmentMetaEntity> getSegmentMetaEntity(@PathVariable("segment_id") String segment_id) {
        Optional<TSegmentMetaEntity> item = tSegmentMetaRepository.findById(segment_id);
        if (item.isPresent()) {
            return new GetMessageResponse<TSegmentMetaEntity>(0, "", item.get());
        }

        return new GetMessageResponse<TSegmentMetaEntity>(-1, "has no record:" + segment_id, null);
    }

    @PostMapping("")
    public MessageResponse setSegmentMetaEntity(@Validated @RequestBody TSegmentMetaEntity segment_meta, BindingResult check) {
        if (check.hasErrors()) {
            return new MessageResponse(-1, check.getAllErrors().get(0).getDefaultMessage());
        }

        TSegmentMetaEntity item = tSegmentMetaRepository.findByName(segment_meta.getName());
        if (null != item) {
            return new MessageResponse(-1, "name:" + segment_meta.getName() + " is exists");
        }

        SegmentRulesBean rules_bean = null;

        try {
            ObjectMapper mapper = new ObjectMapper();
            rules_bean = mapper.readValue(segment_meta.getRules() , SegmentRulesBean.class);

        } catch (Exception e) {
            return new MessageResponse(-1, "rules:" + segment_meta.getRules() + " is invalid");
        }

        segment_meta.setCreateUserName("admin");
        segment_meta.setCreateGroupName("group");
        segment_meta.setPlatformId(1);
        segment_meta.setPlatformName("infinivision");

        try {
            tSegmentMetaRepository.save(segment_meta);
            insertRules(segment_meta, rules_bean, System.currentTimeMillis());
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @PutMapping("/{segment_id}")
    public MessageResponse updateSegmentMetaEntity(@PathVariable("segment_id") String segment_id, @RequestBody TSegmentMetaEntity segment_meta) {
        if (null == segment_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TSegmentMetaEntity> item = tSegmentMetaRepository.findById(segment_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + segment_id + " is not exists");
        }

        try {
            segment_meta.setUpdateTime(System.currentTimeMillis());
            tSegmentMetaRepository.save(item.get().merge(segment_meta));
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @DeleteMapping("/{segment_id}")
    public MessageResponse deleteSegmentMetaEntity(@PathVariable("segment_id") String segment_id) {
        if (null == segment_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TSegmentMetaEntity> item = tSegmentMetaRepository.findById(segment_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + segment_id + " is not exists");
        }

        try {
            tSegmentMetaRepository.deleteById(segment_id);
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @GetMapping("/list")
    public GetListMessageResponse<TSegmentMetaEntity> getTSegmentMetaList(@RequestParam("page") Integer page,
                                                                  @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updateTime"));

        Page<TSegmentMetaEntity> page_list = tSegmentMetaRepository.findAll(pageable);

        return new GetListMessageResponse<TSegmentMetaEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }

    @GetMapping("/{segment_id}/customers")
    public GetListMessageResponse<TCustomerEntity> GetCustomersBySegmentId(@PathVariable("segment_id") String segment_id,
                                                                       @RequestParam("page") Integer page,
                                                                       @RequestParam("size") Integer size) {
        Optional<TSegmentMetaEntity> item = tSegmentMetaRepository.findById(segment_id);
        if (!item.isPresent()) {
            return new GetListMessageResponse<TCustomerEntity>(-1, "segment id:" + segment_id + " is not exists", page, size, new Long(0), null);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("customer_id"));

        Page<BigInteger> page_raw_customer_ids = tTagRepository.getTagCustomerIdsByTagId(segment_id, pageable);

        int customer_size = page_raw_customer_ids.getContent().size();
        if (0 >= customer_size) {
            return new GetListMessageResponse<TCustomerEntity>(-1, "segment id:" + segment_id + " has no customers", page, size, new Long(0), null);
        }

        List<Long> customer_ids = new ArrayList<>();
        for (int i = 0 ; i < customer_size; ++i) {
            customer_ids.add(page_raw_customer_ids.getContent().get(i).longValue());
        }

        List<TCustomerEntity> customers = tCustomerRepository.findByIdInOrderById(customer_ids);

        return new GetListMessageResponse<TCustomerEntity>(0, "", page, size, page_raw_customer_ids.getTotalElements(), customers);
    }

    private void insertRule(TSegmentMetaEntity segment_meta, SegmentRuleBean rule, Long now) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(now);
        String cur_date = dateFormat.format(date);
        if ("AND" == rule.getLogic().toUpperCase()) {
            tTagRepository.insertSegmentLogicAnd(rule.getIds(), segment_meta.getId(), cur_date, now, segment_meta.getCreateUserName(), segment_meta.getCreateGroupName(), segment_meta.getPlatformId(), segment_meta.getPlatformName());
        } else {
            tTagRepository.insertSegmentLogicOr(rule.getIds(), segment_meta.getId(), cur_date, now, segment_meta.getCreateUserName(), segment_meta.getCreateGroupName(), segment_meta.getPlatformId(), segment_meta.getPlatformName());
        }
    }

    private void updateRule(TSegmentMetaEntity segment_meta, String logic, SegmentRuleBean rule, Long now) {
        if ("AND" == logic) {
            if ("AND" == rule.getLogic().toUpperCase()) {
                tTagRepository.updateTagLogicAndAnd(rule.getIds(), segment_meta.getId(), now);
            } else {
                tTagRepository.updateTagLogicAndOr(rule.getIds(), segment_meta.getId(), now);
            }
        } else {
            if ("AND" == rule.getLogic().toUpperCase()) {
                tTagRepository.updateTagLogicOrAnd(rule.getIds(), segment_meta.getId(), now);
            } else {
                tTagRepository.updateTagLogicOrOr(rule.getIds(), segment_meta.getId(), now);
            }
        }
    }

    private void insertRules(TSegmentMetaEntity segment_meta, SegmentRulesBean rules, Long now) {
        boolean is_exist = false;
        for (SegmentRuleBean item: rules.getRules()) {
            if (is_exist) {
                updateRule(segment_meta, rules.getLogic(), item, now);
            } else {
                is_exist = true;
                insertRule(segment_meta, item, now);
            }
        }
    }
}
