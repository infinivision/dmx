package com.dmx.api.controller.meta;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.meta.TEventMetaRepository;
import com.dmx.api.entity.meta.TEventMetaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

@RestController
@RequestMapping(value = "/event_meta")

public class TEventMetaController {
    @Autowired
    TEventMetaRepository tEventMetaRepository;

    @GetMapping("/{event_id}")
    public GetMessageResponse<TEventMetaEntity> getEventMetaEntity(@PathVariable("event_id") String event_id) {
        Optional<TEventMetaEntity> item = tEventMetaRepository.findById(event_id);
        if (item.isPresent()) {
            return new GetMessageResponse<TEventMetaEntity>(0, "", item.get());
        }

        return new GetMessageResponse<TEventMetaEntity>(-1, "has no record:" + event_id, null);
    }

    @PostMapping("")
    public MessageResponse setEventMetaEntity(@Validated @RequestBody TEventMetaEntity event, BindingResult check) {
        if (check.hasErrors()) {
            return new MessageResponse(-1, check.getAllErrors().get(0).getDefaultMessage());
        }

        TEventMetaEntity item = tEventMetaRepository.findByName(event.getName());
        if (null != item) {
            return new MessageResponse(-1, "name:" + event.getName() + " is exists");
        }

        try {
            tEventMetaRepository.save(event);
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @PutMapping("/{event_id}")
    public MessageResponse updateEventMetaEntity(@PathVariable("event_id") String event_id, @RequestBody TEventMetaEntity event) {
        if (null == event_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TEventMetaEntity> item = tEventMetaRepository.findById(event_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + event_id + " is not exists");
        }

        try {
            event.setUpdateTime(System.currentTimeMillis());
            tEventMetaRepository.save(item.get().merge(event));
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @DeleteMapping("/{event_id}")
    public MessageResponse deleteEventMetaEntity(@PathVariable("event_id") String event_id) {
        if (null == event_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TEventMetaEntity> item = tEventMetaRepository.findById(event_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + event_id + " is not exists");
        }

        try {
            tEventMetaRepository.deleteById(event_id);
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @GetMapping("/list")
    public GetListMessageResponse<TEventMetaEntity> getEventMetaList(@RequestParam("page") Integer page,
                                                                     @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updateTime"));

        Page<TEventMetaEntity> page_list = tEventMetaRepository.findAll(pageable);

        return new GetListMessageResponse<TEventMetaEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }
}
