package com.dmx.api.controller.meta;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.analysis.TCustomerRepository;
import com.dmx.api.dao.analysis.TTagRepository;
import com.dmx.api.dao.meta.TTagMetaRepository;
import com.dmx.api.entity.analysis.TCustomerEntity;
import com.dmx.api.entity.meta.TSegmentMetaEntity;
import com.dmx.api.entity.meta.TTagMetaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping(value = "/tag")

public class TTagMetaController {
    @Autowired
    TTagMetaRepository tTagMetaRepository;

    @Autowired
    TTagRepository tTagRepository;

    @Autowired
    TCustomerRepository tCustomerRepository;

    @GetMapping("/{tag_id}")
    public GetMessageResponse<TTagMetaEntity> getTagMetaEntity(@PathVariable("tag_id") String tag_id) {
        Optional<TTagMetaEntity> item = tTagMetaRepository.findById(tag_id);
        if (item.isPresent()) {
            return new GetMessageResponse<TTagMetaEntity>(0, "", item.get());
        }

        return new GetMessageResponse<TTagMetaEntity>(-1, "has no record:" + tag_id, null);
    }

    @GetMapping("/is_name_exists")
    public MessageResponse getTagMetaEntityByName(@RequestParam("name") String name) {
        TTagMetaEntity item = tTagMetaRepository.findByName(name);

        if (null == item) {
            return new MessageResponse(-1, "has no record:" + name);
        }

        return new MessageResponse(0, "");
    }

    @PostMapping("")
    public MessageResponse setTagMetaEntity(@Validated @RequestBody TTagMetaEntity tag_meta, BindingResult check) {
        if (check.hasErrors()) {
            return new MessageResponse(-1, check.getAllErrors().get(0).getDefaultMessage());
        }

        TTagMetaEntity item = tTagMetaRepository.findByName(tag_meta.getName());
        if (null != item) {
            return new MessageResponse(-1, "name:" + tag_meta.getName() + " is exists");
        }

        tag_meta.setCreateUserName("admin");
        tag_meta.setCreateGroupName("group");
        tag_meta.setPlatformId(1);
        tag_meta.setPlatformName("infinivision");

        tTagMetaRepository.save(tag_meta);

        return new MessageResponse(0, "");
    }

    @PostMapping("/copy/{tag_id}")
    public GetMessageResponse<Map<String, String>> copyTagMetaEntity(@PathVariable("tag_id") String tag_id) {
        Optional<TTagMetaEntity> item = tTagMetaRepository.findById(tag_id);
        if (!item.isPresent()) {
            return new GetMessageResponse(-1, "id:" + tag_id + " is not exists", null);
        }

        TTagMetaEntity tag_meta = item.get();

        String name = tag_meta.getName() + "_copy";

        TTagMetaEntity item_by_name = tTagMetaRepository.findByName(name);
        if (null != item_by_name) {
            return new GetMessageResponse(-1, "name:" + name + " is exists", null);
        }

        TTagMetaEntity new_item = new TTagMetaEntity();

        new_item.setName(name);
        new_item.setRules(tag_meta.getRules());
        new_item.setCategory(tag_meta.getCategory());
        new_item.setType(tag_meta.getType());
        new_item.setCount(tag_meta.getCount());
        new_item.setIsSystem(tag_meta.getIsSystem());
        new_item.setIsStatic(tag_meta.getIsStatic());
        new_item.setDescription(tag_meta.getDescription());
        new_item.setCreateUserName(tag_meta.getCreateUserName());
        new_item.setCreateGroupName(tag_meta.getCreateGroupName());
        new_item.setPlatformId(tag_meta.getPlatformId());
        new_item.setPlatformName(tag_meta.getPlatformName());

        tTagMetaRepository.save(new_item);

        Map<String, String> result = new HashMap<>();
        result.put("id", new_item.getId());

        return new GetMessageResponse(0, "", result);
    }

    @PutMapping("/{tag_id}")
    public MessageResponse updateTagMetaEntity(@PathVariable("tag_id") String tag_id, @RequestBody TTagMetaEntity tag_meta) {
        if (null == tag_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TTagMetaEntity> item = tTagMetaRepository.findById(tag_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + tag_id + " is not exists");
        }

        tag_meta.setUpdateTime(System.currentTimeMillis());
        tTagMetaRepository.save(item.get().merge(tag_meta));

        return new MessageResponse(0, "");
    }

    @DeleteMapping("/{tag_id}")
    public MessageResponse deleteTagMetaEntity(@PathVariable("tag_id") String tag_id) {
        if (null == tag_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TTagMetaEntity> item = tTagMetaRepository.findById(tag_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + tag_id + " is not exists");
        }

        tTagMetaRepository.deleteById(tag_id);

        return new MessageResponse(0, "");
    }

    @GetMapping("/list")
    public GetListMessageResponse<TTagMetaEntity> getTTagMetaList(@RequestParam("page") Integer page,
                                                                  @RequestParam("size") Integer size,
                                                                  @RequestParam(value = "sort", required = false, defaultValue = "updateTime") String sort,
                                                                  @RequestParam(value = "dir", required = false, defaultValue = "desc") String dir) {

        if (!sort.equalsIgnoreCase("count") && !sort.equalsIgnoreCase("updateTime")) {
            return new GetListMessageResponse<TTagMetaEntity>(-1, "sort by " + sort + " is not support", page, size, new Long(0), null);
        }

        if (!dir.equalsIgnoreCase("desc") && !dir.equalsIgnoreCase("asc")) {
            return new GetListMessageResponse<TTagMetaEntity>(-1, "directive by " + sort + " is not support", page, size, new Long(0), null);
        }

        Pageable pageable = dir.equalsIgnoreCase("desc")?PageRequest.of(page, size, Sort.by(Sort.Order.desc(sort))):PageRequest.of(page, size, Sort.by(Sort.Order.asc(sort)));

        Page<TTagMetaEntity> page_list = tTagMetaRepository.findAll(pageable);

        return new GetListMessageResponse<TTagMetaEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }

    @GetMapping("/{tag_id}/customers")
    public GetListMessageResponse<TCustomerEntity> GetCustomersByTagId(@PathVariable("tag_id") String tag_id,
                                                                       @RequestParam("page") Integer page,
                                                                       @RequestParam("size") Integer size) {

        Optional<TTagMetaEntity> item = tTagMetaRepository.findById(tag_id);
        if (!item.isPresent()) {
            return new GetListMessageResponse<TCustomerEntity>(-1, "tag id:" + tag_id + " is not exists", page, size, new Long(0), null);
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("customer_id"));

        Page<BigInteger> page_raw_customer_ids = tTagRepository.getTagCustomerIdsByTagId(tag_id, pageable);
        int customer_size = page_raw_customer_ids.getContent().size();
        if (0 >= customer_size) {
            return new GetListMessageResponse<TCustomerEntity>(-1, "tag id:" + tag_id + " has no customers", page, size, new Long(0), null);
        }

        List<Long> customer_ids = new ArrayList<>();
        for (int i = 0 ; i < customer_size; ++i) {
            customer_ids.add(page_raw_customer_ids.getContent().get(i).longValue());
        }

        List<TCustomerEntity> customers = tCustomerRepository.findByIdInOrderById(customer_ids);

        return new GetListMessageResponse<TCustomerEntity>(0, "", page, size, page_raw_customer_ids.getTotalElements(), customers);
    }

    @GetMapping("/query_by_category/{category_id}")
    public GetListMessageResponse<TTagMetaEntity> getTTagMetaListByCategory(
                                                                    @PathVariable("category_id") String category_id,
                                                                    @RequestParam("page") Integer page,
                                                                    @RequestParam("size") Integer size,
                                                                    @RequestParam(value = "sort", required = false, defaultValue = "updateTime") String sort,
                                                                    @RequestParam(value = "dir", required = false, defaultValue = "desc") String dir) {
        if (!sort.equalsIgnoreCase("count") && !sort.equalsIgnoreCase("updateTime")) {
            return new GetListMessageResponse<TTagMetaEntity>(-1, "sort by " + sort + " is not support", page, size, new Long(0), null);
        }

        if (!dir.equalsIgnoreCase("desc") && !dir.equalsIgnoreCase("asc")) {
            return new GetListMessageResponse<TTagMetaEntity>(-1, "directive by " + sort + " is not support", page, size, new Long(0), null);
        }

        Pageable pageable = dir.equalsIgnoreCase("desc")?PageRequest.of(page, size, Sort.by(Sort.Order.desc(sort))):PageRequest.of(page, size, Sort.by(Sort.Order.asc(sort)));

        Page<TTagMetaEntity> page_list = tTagMetaRepository.findByCategory(category_id, pageable);

        return new GetListMessageResponse<TTagMetaEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }

    @GetMapping("/query_by_name")
    public GetListMessageResponse<TTagMetaEntity> getTTagMetaListByName(
            @RequestParam("name") String name,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size,
            @RequestParam(value = "sort", required = false, defaultValue = "updateTime") String sort,
            @RequestParam(value = "dir", required = false, defaultValue = "desc") String dir) {
        if (!sort.equalsIgnoreCase("count") && !sort.equalsIgnoreCase("updateTime")) {
            return new GetListMessageResponse<TTagMetaEntity>(-1, "sort by " + sort + " is not support", page, size, new Long(0), null);
        }

        if (!dir.equalsIgnoreCase("desc") && !dir.equalsIgnoreCase("asc")) {
            return new GetListMessageResponse<TTagMetaEntity>(-1, "directive by " + sort + " is not support", page, size, new Long(0), null);
        }

        Pageable pageable = dir.equalsIgnoreCase("desc")?PageRequest.of(page, size, Sort.by(Sort.Order.desc(sort))):PageRequest.of(page, size, Sort.by(Sort.Order.asc(sort)));

        Page<TTagMetaEntity> page_list = tTagMetaRepository.findByNameContains(name, pageable);

        return new GetListMessageResponse<TTagMetaEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }
}
