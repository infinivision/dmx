package com.dmx.api.service;

import com.dmx.api.ApiApplication;
import com.dmx.api.dao.analysis.TTagRepository;
import com.dmx.api.dao.meta.TTagMetaRepository;
import com.dmx.api.entity.meta.TTagMetaEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TagCalculateService {
    @Autowired
    TTagMetaRepository tTagMetaRepository;

    @Autowired
    TTagRepository tTagRepository;

    @Autowired
    private Environment env;

    private static final Logger logger = LogManager.getLogger(ApiApplication.class);

    // one hour
    @Scheduled(cron = "0 0 3 * * *")
    public void CalculateSegments() {
        Page<TTagMetaEntity> page_tag_list = null;

        Integer page = 0;
        Integer size = Integer.parseInt(env.getProperty("application.tag.size"));
        if (0 >= size) {
            size = 100;
        }

        do {
            try {
                Pageable pageable = PageRequest.of(page, size, Sort.by("id"));
                page_tag_list = tTagMetaRepository.findAll(pageable);
                List<TTagMetaEntity> list = page_tag_list.getContent();

                List<String> ids = new ArrayList<>();
                for (int i = 0; i < list.size(); ++i) {
                    ids.add(list.get(i).getId());
                }

                List<Object[]> result_list = tTagRepository.getTagCountByIds(ids);
                for (int i = 0, j = 0; (i < list.size()) && (j < result_list.size()); ++i) {
                    Object[] item = result_list.get(j);
                    TTagMetaEntity tag_meta = list.get(i);

                    String id = String.valueOf(item[0]);
                    String customer_count = String.valueOf(item[1]);

                    if (tag_meta.getId().equals(id)) {
                        list.get(i).setUpdateTime(System.currentTimeMillis() / 1000);
                        list.get(i).setCount(Long.parseLong(customer_count));
                        ++j;
                    } else {
                        logger.error("list id:" + list.get(i).getId() + " tag id:" + id);
                    }
                }

                tTagMetaRepository.saveAll(list);

                ++page;
            } catch (Exception e) {
                logger.error("tag task schedule failed:" + e.getMessage());
            }
        } while (page_tag_list.getContent().size() >= size);
    }
}
