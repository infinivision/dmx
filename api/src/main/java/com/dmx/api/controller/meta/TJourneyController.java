package com.dmx.api.controller.meta;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.meta.TJourneyRepository;
import com.dmx.api.entity.meta.TJourneyEntity;
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
@RequestMapping(value = "/journey")

public class TJourneyController {
    @Autowired
    TJourneyRepository tJourneyRepository;

    @GetMapping("/{journey_id}")
    public GetMessageResponse<TJourneyEntity> getJourneyEntity(@PathVariable("journey_id") String journey_id) {
        Optional<TJourneyEntity> item = tJourneyRepository.findById(journey_id);
        if (item.isPresent()) {
            return new GetMessageResponse<TJourneyEntity>(0, "", item.get());
        }

        return new GetMessageResponse<TJourneyEntity>(-1, "has no record:" + journey_id, null);
    }

    @PostMapping("")
    public MessageResponse setJourneyEntity(@Validated @RequestBody TJourneyEntity journey, BindingResult check) {
        if (check.hasErrors()) {
            return new MessageResponse(-1, check.getAllErrors().get(0).getDefaultMessage());
        }

        TJourneyEntity item = tJourneyRepository.findByName(journey.getName());
        if (null != item) {
            return new MessageResponse(-1, "name:" + journey.getName() + " is exists");
        }

        tJourneyRepository.save(journey);

        return new MessageResponse(0, "");
    }

    @PutMapping("/{journey_id}")
    public MessageResponse updateJourneyEntity(@PathVariable("journey_id") String journey_id, @RequestBody TJourneyEntity journey) {
        if (null == journey_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TJourneyEntity> item = tJourneyRepository.findById(journey_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + journey_id + " is not exists");
        }

        journey.setUpdateTime(System.currentTimeMillis());
        tJourneyRepository.save(item.get().merge(journey));

        return new MessageResponse(0, "");
    }

    @DeleteMapping("/{journey_id}")
    public MessageResponse deleteJourneyEntity(@PathVariable("journey_id") String journey_id) {
        if (null == journey_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TJourneyEntity> item = tJourneyRepository.findById(journey_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + journey_id + " is not exists");
        }

        tJourneyRepository.deleteById(journey_id);

        return new MessageResponse(0, "");
    }

    @GetMapping("/list")
    public GetListMessageResponse<TJourneyEntity> getJourneyList(@RequestParam("page") Integer page,
                                                                 @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updateTime"));

        Page<TJourneyEntity> page_list = tJourneyRepository.findAll(pageable);

        return new GetListMessageResponse<TJourneyEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }
}
