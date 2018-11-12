package com.dmx.api.controller;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.TDashboardChartRepository;
import com.dmx.api.entity.TDashboardChartEntity;
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
@RequestMapping(value = "/dashboard")

public class TDashboardChartController {
    @Autowired
    TDashboardChartRepository tDashboardChartRepository;

    @GetMapping("/{dashboard_chart_id}")
    public GetMessageResponse<TDashboardChartEntity> getDashboardChartEntity(@PathVariable("dashboard_chart_id") String dashboard_chart_id) {
        Optional<TDashboardChartEntity> item = tDashboardChartRepository.findById(dashboard_chart_id);
        if (item.isPresent()) {
            return new GetMessageResponse<TDashboardChartEntity>(0, "", item.get());
        }

        return new GetMessageResponse<TDashboardChartEntity>(-1, "has no record:" + dashboard_chart_id, null);
    }

    @PostMapping("")
    public MessageResponse setDashboardChartEntity(@Validated @RequestBody TDashboardChartEntity chart, BindingResult check) {
        if (check.hasErrors()) {
            return new MessageResponse(-1, check.getAllErrors().get(0).getDefaultMessage());
        }

        TDashboardChartEntity item = tDashboardChartRepository.findByName(chart.getName());
        if (null != item) {
            return new MessageResponse(-1, "name:" + chart.getName() + " is exists");
        }

        try {
            tDashboardChartRepository.save(chart);
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @PutMapping("/{dashboard_chart_id}")
    public MessageResponse updateDashboardChartEntity(@PathVariable("dashboard_chart_id") String dashboard_chart_id, @RequestBody TDashboardChartEntity chart) {
        if (null == dashboard_chart_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TDashboardChartEntity> item = tDashboardChartRepository.findById(dashboard_chart_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + dashboard_chart_id + " is not exists");
        }

        try {
            chart.setUpdateTime(System.currentTimeMillis());
            tDashboardChartRepository.save(item.get().merge(chart));
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @DeleteMapping("/{dashboard_chart_id}")
    public MessageResponse deleteDashboardChartEntity(@PathVariable("dashboard_chart_id") String dashboard_chart_id) {
        if (null == dashboard_chart_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TDashboardChartEntity> item = tDashboardChartRepository.findById(dashboard_chart_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + dashboard_chart_id + " is not exists");
        }

        try {
            tDashboardChartRepository.deleteById(dashboard_chart_id);
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @GetMapping("/list")
    public GetListMessageResponse<TDashboardChartEntity> getDashboardChartList(@RequestParam("page") Integer page,
                                                                     @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updateTime"));

        Page<TDashboardChartEntity> page_list = tDashboardChartRepository.findAll(pageable);

        return new GetListMessageResponse<TDashboardChartEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }
}
