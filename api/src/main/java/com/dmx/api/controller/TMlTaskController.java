package com.dmx.api.controller;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.TMlTaskRepository;
import com.dmx.api.entity.TMlTaskEntity;
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
@RequestMapping(value = "/ml_task")

public class TMlTaskController {
    @Autowired
    TMlTaskRepository tMlTaskRepository;

    @GetMapping("/{task_id}")
    public GetMessageResponse<TMlTaskEntity> getMlTaskEntity(@PathVariable("task_id") String task_id) {
        Optional<TMlTaskEntity> item = tMlTaskRepository.findById(task_id);
        if (item.isPresent()) {
            return new GetMessageResponse<TMlTaskEntity>(0, "", item.get());
        }

        return new GetMessageResponse<TMlTaskEntity>(-1, "has no record:" + task_id, null);
    }

    @PostMapping("")
    public MessageResponse setMlTaskEntity(@Validated @RequestBody TMlTaskEntity ml_task, BindingResult check) {
        if (check.hasErrors()) {
            return new MessageResponse(-1, check.getAllErrors().get(0).getDefaultMessage());
        }

        TMlTaskEntity item = tMlTaskRepository.findByName(ml_task.getName());
        if (null != item) {
            return new MessageResponse(-1, "name:" + ml_task.getName() + " is exists");
        }

        try {
            tMlTaskRepository.save(ml_task);
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @PutMapping("/{task_id}")
    public MessageResponse updateMlTaskEntity(@PathVariable("task_id") String task_id, @RequestBody TMlTaskEntity ml_task) {
        if (null == task_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TMlTaskEntity> item = tMlTaskRepository.findById(task_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + task_id + " is not exists");
        }

        try {
            ml_task.setUpdateTime(System.currentTimeMillis());
            tMlTaskRepository.save(item.get().merge(ml_task));
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @DeleteMapping("/{task_id}")
    public MessageResponse deleteMlTaskEntity(@PathVariable("task_id") String task_id) {
        if (null == task_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TMlTaskEntity> item = tMlTaskRepository.findById(task_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + task_id + " is not exists");
        }

        try {
            tMlTaskRepository.deleteById(task_id);
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }


    @GetMapping("/list")
    public GetListMessageResponse<TMlTaskEntity> getTTagMetaList(@RequestParam("page") Integer page,
                                                                  @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updateTime"));

        Page<TMlTaskEntity> page_list = tMlTaskRepository.findAll(pageable);

        return new GetListMessageResponse<TMlTaskEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }
}
