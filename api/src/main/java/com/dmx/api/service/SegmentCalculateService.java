package com.dmx.api.service;

import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.dao.analysis.TTagRepository;
import com.dmx.api.dao.meta.TSegmentMetaRepository;
import com.dmx.api.entity.analysis.TTagEntity;
import com.dmx.api.entity.meta.TSegmentMetaEntity;
import com.dmx.api.entity.meta.TTagMetaEntity;
import com.dmx.api.service.bean.SegmentRuleBean;
import com.dmx.api.service.bean.SegmentRulesBean;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class SegmentCalculateService {
    @Autowired
    TSegmentMetaRepository tSegmentMetaRepository;

    @Autowired
    TTagRepository tTagRepository;

    @Scheduled(fixedRate = 10000)
    public void CalculateSegments() {
        Page<TSegmentMetaEntity> page_segment_list;

        Integer page = 0;
        Integer size = 10;
        Long now = System.currentTimeMillis();

        do {
            Pageable pageable = PageRequest.of(page, size, Sort.by("updateTime"));
            page_segment_list = tSegmentMetaRepository.findAll(pageable);
            List<TSegmentMetaEntity> list = page_segment_list.getContent();

            for (int i = 0; i < list.size(); ++i) {
                System.out.print(" **************************************** size: " + list.size());
            }

            ++page;
        } while (page_segment_list.getContent().size() >= size);
    }


    private  List<String> getTagIdsFromRules(SegmentRulesBean rules) {
        List<String> result = new ArrayList<>();
        for (SegmentRuleBean item: rules.getRules()) {
            for (String tag_id: item.getIds()) {
                result.add(tag_id);
            }
        }
        return result;
    }
}
