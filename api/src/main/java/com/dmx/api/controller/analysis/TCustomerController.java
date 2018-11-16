package com.dmx.api.controller.analysis;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.dao.analysis.TCustomerRepository;
import com.dmx.api.entity.analysis.TCustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/customer")

public class TCustomerController {
    @Autowired
    TCustomerRepository tCustomerRepository;

    @GetMapping("/{customer_id}")
    public GetMessageResponse<TCustomerEntity> getCustomerEntity(@PathVariable("customer_id") String customer_id) {
        Optional<TCustomerEntity> item = tCustomerRepository.findById(customer_id);
        if (item.isPresent()) {
            return new GetMessageResponse<TCustomerEntity>(0, "", item.get());
        }

        return new GetMessageResponse<TCustomerEntity>(-1, "has no record:" + customer_id, null);
    }

    @GetMapping("/list")
    public GetListMessageResponse<TCustomerEntity> getCustomerList(@RequestParam("page") Integer page,
                                                                               @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updateTime"));

        Page<TCustomerEntity> page_list = tCustomerRepository.findAll(pageable);

        return new GetListMessageResponse<TCustomerEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }
}
