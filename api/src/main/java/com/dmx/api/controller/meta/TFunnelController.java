package com.dmx.api.controller.meta;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.meta.TFunnelRepository;
import com.dmx.api.entity.meta.TFunnelEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/funnel")

public class TFunnelController {
    @Autowired
    TFunnelRepository tFunnelRepository;

    @GetMapping("/{funnel_id}")
    public GetMessageResponse<TFunnelEntity> getFunnelEntity(@PathVariable("funnel_id") String funnel_id) {
        Optional<TFunnelEntity> item = tFunnelRepository.findById(funnel_id);
        if (item.isPresent()) {
            return new GetMessageResponse<TFunnelEntity>(0, "", item.get());
        }

        return new GetMessageResponse<TFunnelEntity>(-1, "has no record:" + funnel_id, null);
    }

    @PostMapping("")
    public MessageResponse setFunnelEntity(@Validated @RequestBody TFunnelEntity funnel, BindingResult check) {
        if (check.hasErrors()) {
            return new MessageResponse(-1, check.getAllErrors().get(0).getDefaultMessage());
        }

        TFunnelEntity item = tFunnelRepository.findByName(funnel.getName());
        if (null != item) {
            return new MessageResponse(-1, "name:" + funnel.getName() + " is exists");
        }

        tFunnelRepository.save(funnel);

        return new MessageResponse(0, "");
    }

    @PutMapping("/{funnel_id}")
    public MessageResponse updateFunnelEntity(@PathVariable("funnel_id") String funnel_id, @RequestBody TFunnelEntity journey) {
        if (null == funnel_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TFunnelEntity> item = tFunnelRepository.findById(funnel_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + funnel_id + " is not exists");
        }

        journey.setUpdateTime(System.currentTimeMillis());
        tFunnelRepository.save(item.get().merge(journey));

        return new MessageResponse(0, "");
    }

    @DeleteMapping("/{funnel_id}")
    public MessageResponse deleteFunnelEntity(@PathVariable("funnel_id") String funnel_id) {
        if (null == funnel_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TFunnelEntity> item = tFunnelRepository.findById(funnel_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + funnel_id + " is not exists");
        }

        tFunnelRepository.deleteById(funnel_id);

        return new MessageResponse(0, "");
    }

    @GetMapping("/list")
    public GetListMessageResponse<TFunnelEntity> getFunnelList(@RequestParam("page") Integer page,
                                                                 @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updateTime"));

        Page<TFunnelEntity> page_list = tFunnelRepository.findAll(pageable);

        return new GetListMessageResponse<TFunnelEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }
}
