package com.dmx.api.controller.meta;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.analysis.TCustomerRepository;
import com.dmx.api.dao.analysis.TTagRepository;
import com.dmx.api.dao.meta.TTagMetaRepository;
import com.dmx.api.entity.analysis.TCustomerEntity;
import com.dmx.api.entity.meta.TTagMetaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/tag")

public class TTagMetaController {
    @Autowired
    TTagMetaRepository tTagMetaRepository;

    @Autowired
    TTagRepository tTagRepository;

    @Autowired
    TCustomerRepository tCustomerRepository;

    @GetMapping("/{tag_id}")
    public GetMessageResponse<TTagMetaEntity> getTagMetaEntity(@PathVariable("tag_id") String tag_id) {
        Optional<TTagMetaEntity> item = tTagMetaRepository.findById(tag_id);
        if (item.isPresent()) {
            return new GetMessageResponse<TTagMetaEntity>(0, "", item.get());
        }

        return new GetMessageResponse<TTagMetaEntity>(-1, "has no record:" + tag_id, null);
    }

    @PostMapping("")
    public MessageResponse setTagMetaEntity(@Validated @RequestBody TTagMetaEntity tag_meta, BindingResult check) {
        if (check.hasErrors()) {
            return new MessageResponse(-1, check.getAllErrors().get(0).getDefaultMessage());
        }

        TTagMetaEntity item = tTagMetaRepository.findByName(tag_meta.getName());
        if (null != item) {
            return new MessageResponse(-1, "name:" + tag_meta.getName() + " is exists");
        }

        try {
            tTagMetaRepository.save(tag_meta);
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @PutMapping("/{tag_id}")
    public MessageResponse updateTagMetaEntity(@PathVariable("tag_id") String tag_id, @RequestBody TTagMetaEntity tag_meta) {
        if (null == tag_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TTagMetaEntity> item = tTagMetaRepository.findById(tag_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + tag_id + " is not exists");
        }

        try {
            tag_meta.setUpdateTime(System.currentTimeMillis());
            tTagMetaRepository.save(item.get().merge(tag_meta));
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @DeleteMapping("/{tag_id}")
    public MessageResponse deleteTagMetaEntity(@PathVariable("tag_id") String tag_id) {
        if (null == tag_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TTagMetaEntity> item = tTagMetaRepository.findById(tag_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + tag_id + " is not exists");
        }

        try {
            tTagMetaRepository.deleteById(tag_id);
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @GetMapping("/list")
    public GetListMessageResponse<TTagMetaEntity> getTTagMetaList(@RequestParam("page") Integer page,
                                                                  @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updateTime"));

        Page<TTagMetaEntity> page_list = tTagMetaRepository.findAll(pageable);

        return new GetListMessageResponse<TTagMetaEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }

    @GetMapping("/{tag_id}/customers")
    public GetListMessageResponse<TCustomerEntity> GetCustomersByTagId(@PathVariable("tag_id") String tag_id,
                                                                       @RequestParam("page") Integer page,
                                                                       @RequestParam("size") Integer size) {

        Optional<TTagMetaEntity> item = tTagMetaRepository.findById(tag_id);
        if (!item.isPresent()) {
            return new GetListMessageResponse<TCustomerEntity>(-1, "tag id:" + tag_id + " is not exists", page, size, new Long(0), null);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("customer_id"));

        Page<BigInteger> page_raw_customer_ids = tTagRepository.getTagCustomerIdsByTagId(tag_id, pageable);
        int customer_size = page_raw_customer_ids.getContent().size();
        if (0 >= customer_size) {
            return new GetListMessageResponse<TCustomerEntity>(-1, "tag id:" + tag_id + " has no customers", page, size, new Long(0), null);
        }

        List<Long> customer_ids = new ArrayList<>();
        for (int i = 0 ; i < customer_size; ++i) {
            customer_ids.add(page_raw_customer_ids.getContent().get(i).longValue());
        }

        List<TCustomerEntity> customers = tCustomerRepository.findByIdInOrderById(customer_ids);

        return new GetListMessageResponse<TCustomerEntity>(0, "", page, size, page_raw_customer_ids.getTotalElements(), customers);
    }
}
