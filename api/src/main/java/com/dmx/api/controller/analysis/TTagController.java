package com.dmx.api.controller.analysis;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.dao.analysis.TCustomerRepository;
import com.dmx.api.dao.analysis.TTagRepository;
import com.dmx.api.entity.analysis.TCustomerEntity;
import com.dmx.api.entity.analysis.TTagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/tag")

public class TTagController {
    @Autowired
    TTagRepository tTagRepository;

    @Autowired
    TCustomerRepository tCustomerRepository;

    @GetMapping("/{tag_id}")
    public GetMessageResponse<TTagEntity> getTagEntity(@PathVariable("tag_id") String tag_id) {
        Optional<TTagEntity> item = tTagRepository.findById(tag_id);
        if (item.isPresent()) {
            return new GetMessageResponse<TTagEntity>(0, "", item.get());
        }

        return new GetMessageResponse<TTagEntity>(-1, "has no record:" + tag_id, null);
    }

    @GetMapping("/{tag_id}/customers")
    public GetListMessageResponse<TCustomerEntity> getTagCustomers(@PathVariable("tag_id") String tag_id,
                                                                   @RequestParam("page") Integer page,
                                                                   @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updateTime"));

        Page<TCustomerEntity> page_list = tCustomerRepository.findAllByIdIn(null, pageable);

        return new GetListMessageResponse<TCustomerEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }
}
