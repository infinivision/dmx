package com.dmx.api.controller.analysis;

import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.dao.analysis.TTagRepository;
import com.dmx.api.entity.analysis.TTagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/tag")

public class TTagController {
    @Autowired
    TTagRepository tTagRepository;

    @GetMapping("/{tag_id}")
    public GetMessageResponse<TTagEntity> getTagEntity(@PathVariable("tag_id") String tag_id) {
        Optional<TTagEntity> item = tTagRepository.findById(tag_id);
        if (item.isPresent()) {
            return new GetMessageResponse<TTagEntity>(0, "", item.get());
        }

        return new GetMessageResponse<TTagEntity>(-1, "has no record:" + tag_id, null);
    }
}
