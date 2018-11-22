package com.dmx.api.controller.meta;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.GetListNoPageMessageResponse;
import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.meta.TCategoryRepository;
import com.dmx.api.entity.meta.TCategoryEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/category")
public class TCategoryController {
    @Autowired
    TCategoryRepository tCategoryRepository;

    @GetMapping("/{category_id}")
    public GetMessageResponse<TCategoryEntity> getCategoryEntity(@PathVariable("category_id") String category_id) {
        Optional<TCategoryEntity> item = tCategoryRepository.findById(category_id);
        if (item.isPresent()) {
            return new GetMessageResponse<TCategoryEntity>(0, "", item.get());
        }

        return new GetMessageResponse<TCategoryEntity>(-1, "has no record:" + category_id, null);
    }

    @PostMapping("")
    public MessageResponse setCategoryEntity(@Validated @RequestBody TCategoryEntity category, BindingResult check) {
        if (check.hasErrors()) {
            return new MessageResponse(-1, check.getAllErrors().get(0).getDefaultMessage());
        }

        TCategoryEntity item = tCategoryRepository.findByName(category.getName());
        if (null != item) {
            return new MessageResponse(-1, "name:" + category.getName() + " is exists");
        }

        if (null == category.getParent() || 0 >= category.getParent().length()) {
            category.setLevel(0);
        } else {
            Optional<TCategoryEntity> parent = tCategoryRepository.findById(category.getParent());

            if (!parent.isPresent()) {
                return new MessageResponse(-1, "parent:" + category.getParent() + " is not exists");
            }

            category.setLevel(parent.get().getLevel() + 1);
            category.setType(parent.get().getType());
        }

        category.setCreateUserName("admin");
        category.setCreateGroupName("group");
        category.setPlatformId(1);
        category.setPlatformName("infinivision");


        tCategoryRepository.save(category);

        return new MessageResponse(0, "");
    }

    @PutMapping("/{category_id}")
    public MessageResponse updateCategoryEntity(@PathVariable("category_id") String category_id, @RequestBody TCategoryEntity category) {
        if (null == category_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TCategoryEntity> item = tCategoryRepository.findById(category_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + category_id + " is not exists");
        }

        category.setUpdateTime(System.currentTimeMillis());
        tCategoryRepository.save(item.get().merge(category));

        return new MessageResponse(0, "");
    }

    @DeleteMapping("/{category_id}")
    public MessageResponse deleteCategoryEntity(@PathVariable("category_id") String category_id) {
        if (null == category_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TCategoryEntity> item = tCategoryRepository.findById(category_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + category_id + " is not exists");
        }

        tCategoryRepository.deleteById(category_id);
        tCategoryRepository.deleteByCategoryTreeContaining(category_id);

        return new MessageResponse(0, "");
    }

    @GetMapping("/list")
    public GetListMessageResponse<TCategoryEntity> getCategoryList(@RequestParam("page") Integer page,
                                                                  @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updateTime"));

        Page<TCategoryEntity> page_list = tCategoryRepository.findAll(pageable);

        return new GetListMessageResponse<TCategoryEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }

    @GetMapping("/{category_id}/list")
    public GetListNoPageMessageResponse<TCategoryEntity> getChildrenList(@PathVariable("category_id") String category_id) {
        List<TCategoryEntity>   list = tCategoryRepository.findByParent(category_id);

        return new GetListNoPageMessageResponse<TCategoryEntity>(0, "", list);
    }

    @GetMapping("/root")
    public GetListNoPageMessageResponse<TCategoryEntity> getRootCategoryList() {
        List<TCategoryEntity> list = tCategoryRepository.findByLevel(0);

        return new GetListNoPageMessageResponse<TCategoryEntity>(0, "", list);
    }
}
