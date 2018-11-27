package com.dmx.api.controller.meta;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.analysis.TCustomerRepository;
import com.dmx.api.dao.analysis.TTagRepository;
import com.dmx.api.dao.meta.TSegmentMetaRepository;
import com.dmx.api.entity.analysis.TCustomerEntity;
import com.dmx.api.entity.analysis.TTagEntity;
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
import java.util.stream.Collectors;

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

        segment_meta.setCreateUserName("admin");
        segment_meta.setCreateGroupName("group");
        segment_meta.setPlatformId(1);
        segment_meta.setPlatformName("infinivision");

        tSegmentMetaRepository.save(segment_meta);

        if (null != segment_meta.getRules() && 0 < segment_meta.getRules().length()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                SegmentRulesBean rules_bean = mapper.readValue(segment_meta.getRules() , SegmentRulesBean.class);

                insertRules(segment_meta, rules_bean, System.currentTimeMillis());
            } catch (Exception e) {
                return new MessageResponse(-1, "rules:" + segment_meta.getRules() + " is invalid");
            }
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

        segment_meta.setCreateUserName("admin");
        segment_meta.setCreateGroupName("group");
        segment_meta.setPlatformId(1);
        segment_meta.setPlatformName("infinivision");
        segment_meta.setUpdateTime(System.currentTimeMillis());

        tSegmentMetaRepository.save(item.get().merge(segment_meta));

        if (null != segment_meta.getRules() && 0 < segment_meta.getRules().length()) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                SegmentRulesBean rules_bean = mapper.readValue(segment_meta.getRules() , SegmentRulesBean.class);

                updateRules(segment_meta, rules_bean, System.currentTimeMillis());
            } catch (Exception e) {
                return new MessageResponse(-1, "rules:" + segment_meta.getRules() + " is invalid");
            }
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

        tSegmentMetaRepository.deleteById(segment_id);

        Optional<TTagEntity> tag_item = tTagRepository.findById(segment_id);
        if (tag_item.isPresent()) {
            tTagRepository.deleteById(segment_id);
        }


        return new MessageResponse(0, "");
    }

    @GetMapping("/list")
    public GetListMessageResponse<TSegmentMetaEntity> getTSegmentMetaList(@RequestParam("page") Integer page,
                                                                          @RequestParam("size") Integer size,
                                                                          @RequestParam(value = "sort", required = false, defaultValue = "updateTime") String sort,
                                                                          @RequestParam(value = "dir", required = false, defaultValue = "desc") String dir) {
        if (!sort.equalsIgnoreCase("customerCount") && !sort.equalsIgnoreCase("updateTime")) {
            return new GetListMessageResponse<TSegmentMetaEntity>(-1, "sort by " + sort + " is not support", page, size, new Long(0), null);
        }

        if (!dir.equalsIgnoreCase("desc") && !dir.equalsIgnoreCase("asc")) {
            return new GetListMessageResponse<TSegmentMetaEntity>(-1, "directive by " + sort + " is not support", page, size, new Long(0), null);
        }

        Pageable pageable = dir.equalsIgnoreCase("desc")?PageRequest.of(page, size, Sort.by(Sort.Order.desc(sort))):PageRequest.of(page, size, Sort.by(Sort.Order.asc(sort)));

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

        List<Long> customer_ids = page_raw_customer_ids.stream()
                .map(s -> s.longValue())
                .collect(Collectors.toList());

        List<TCustomerEntity> customers = tCustomerRepository.findByIdInOrderById(customer_ids);

        return new GetListMessageResponse<TCustomerEntity>(0, "", page, size, page_raw_customer_ids.getTotalElements(), customers);
    }

    @GetMapping("/query_by_category/{category_id}")
    public GetListMessageResponse<TSegmentMetaEntity> getTSegmentListByCategory(
            @PathVariable("category_id") String category_id,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "updateTime") String sort,
            @RequestParam(value = "dir", required = false, defaultValue = "desc") String dir) {
        if (!sort.equalsIgnoreCase("customerCount") && !sort.equalsIgnoreCase("updateTime")) {
            return new GetListMessageResponse<TSegmentMetaEntity>(-1, "sort by " + sort + " is not support", page, size, new Long(0), null);
        }

        if (!dir.equalsIgnoreCase("desc") && !dir.equalsIgnoreCase("asc")) {
            return new GetListMessageResponse<TSegmentMetaEntity>(-1, "directive by " + sort + " is not support", page, size, new Long(0), null);
        }

        Pageable pageable = dir.equalsIgnoreCase("desc")?PageRequest.of(page, size, Sort.by(Sort.Order.desc(sort))):PageRequest.of(page, size, Sort.by(Sort.Order.asc(sort)));

        Page<TSegmentMetaEntity> page_list = tSegmentMetaRepository.findByCategory(category_id, pageable);

        return new GetListMessageResponse<TSegmentMetaEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }

    @GetMapping("/query_by_name/{name}")
    public GetListMessageResponse<TSegmentMetaEntity> getTSegmentListByName(
            @PathVariable("name") String name,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "updateTime") String sort,
            @RequestParam(value = "dir", required = false, defaultValue = "desc") String dir) {
        if (!sort.equalsIgnoreCase("customerCount") && !sort.equalsIgnoreCase("updateTime")) {
            return new GetListMessageResponse<TSegmentMetaEntity>(-1, "sort by " + sort + " is not support", page, size, new Long(0), null);
        }

        if (!dir.equalsIgnoreCase("desc") && !dir.equalsIgnoreCase("asc")) {
            return new GetListMessageResponse<TSegmentMetaEntity>(-1, "directive by " + sort + " is not support", page, size, new Long(0), null);
        }

        Pageable pageable = dir.equalsIgnoreCase("desc")?PageRequest.of(page, size, Sort.by(Sort.Order.desc(sort))):PageRequest.of(page, size, Sort.by(Sort.Order.asc(sort)));

        Page<TSegmentMetaEntity> page_list = tSegmentMetaRepository.findByNameContains(name, pageable);

        return new GetListMessageResponse<TSegmentMetaEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }

    private void insertRule(TSegmentMetaEntity segment_meta, SegmentRuleBean rule, Long now) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(now);
        String cur_date = dateFormat.format(date);
        if ("AND" == rule.getLogic().toUpperCase()) {
            tTagRepository.insertAnd(rule.getIds(), segment_meta.getId(), cur_date, now, segment_meta.getCreateUserName(), segment_meta.getCreateGroupName(), segment_meta.getPlatformId(), segment_meta.getPlatformName());
        } else {
            tTagRepository.insertOr(rule.getIds(), segment_meta.getId(), cur_date, now, segment_meta.getCreateUserName(), segment_meta.getCreateGroupName(), segment_meta.getPlatformId(), segment_meta.getPlatformName());
        }
    }

    private void updateRule(TSegmentMetaEntity segment_meta, SegmentRuleBean rule, Long now) {
        if ("AND" == rule.getLogic().toUpperCase()) {
            tTagRepository.updateTagAnd(rule.getIds(), segment_meta.getId(), now);
        } else {
            tTagRepository.updateTagOr(rule.getIds(), segment_meta.getId(), now);
        }
    }

    private void addRule(TSegmentMetaEntity segment_meta, String logic, SegmentRuleBean rule, Long now) {
        if ("AND" == logic) {
            if ("AND" == rule.getLogic().toUpperCase()) {
                tTagRepository.updateTagAndAnd(rule.getIds(), segment_meta.getId(), now);
            } else {
                tTagRepository.updateTagAndOr(rule.getIds(), segment_meta.getId(), now);
            }
        } else {
            if ("AND" == rule.getLogic().toUpperCase()) {
                tTagRepository.updateTagOrAnd(rule.getIds(), segment_meta.getId(), now);
            } else {
                tTagRepository.updateTagOrOr(rule.getIds(), segment_meta.getId(), now);
            }
        }
    }

    private void addRules(TSegmentMetaEntity segment_meta, SegmentRulesBean rules, Long now) {
        if (0 >= rules.getRules().size()) {
            return;
        }

        for (SegmentRuleBean item: rules.getRules()) {
            addRule(segment_meta, rules.getLogic(), item, now);
        }
    }

    private void insertRules(TSegmentMetaEntity segment_meta, SegmentRulesBean rules, Long now) {
        if (0 >= rules.getRules().size()) {
            return;
        }

        insertRule(segment_meta, rules.getRules().get(0), now);

        rules.getRules().remove(0);

        addRules(segment_meta, rules, now);
    }

    private void updateRules(TSegmentMetaEntity segment_meta, SegmentRulesBean rules, Long now) {
        if (0 >= rules.getRules().size()) {
            return;
        }

        updateRule(segment_meta, rules.getRules().get(0), now);

        rules.getRules().remove(0);

        addRules(segment_meta, rules, now);
    }
}
