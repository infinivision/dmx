package com.dmx.api.controller.meta;

import com.dmx.api.bean.GetListMessageResponse;
import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.analysis.TCustomerRepository;
import com.dmx.api.dao.analysis.TTagRepository;
import com.dmx.api.dao.meta.TSegmentMetaRepository;
import com.dmx.api.entity.analysis.TCustomerEntity;
import com.dmx.api.entity.meta.TSegmentMetaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/segment")

public class TSegmentMetaController {
    @Autowired
    TSegmentMetaRepository tSegmentMetaRepository;

    @Autowired
    TTagRepository tTagRepository;

    @Autowired
    TCustomerRepository tCustomerRepository;

    @GetMapping("/{segment_id}")
    public GetMessageResponse<TSegmentMetaEntity> getSegmentMetaEntity(@PathVariable("segment_id") String segment_id) {
        Optional<TSegmentMetaEntity> item = tSegmentMetaRepository.findById(segment_id);
        if (item.isPresent()) {
            return new GetMessageResponse<TSegmentMetaEntity>(0, "", item.get());
        }

        return new GetMessageResponse<TSegmentMetaEntity>(-1, "has no record:" + segment_id, null);
    }

    @PostMapping("")
    public MessageResponse setSegmentMetaEntity(@Validated @RequestBody TSegmentMetaEntity segment_meta, BindingResult check) {
        if (check.hasErrors()) {
            return new MessageResponse(-1, check.getAllErrors().get(0).getDefaultMessage());
        }

        TSegmentMetaEntity item = tSegmentMetaRepository.findByName(segment_meta.getName());
        if (null != item) {
            return new MessageResponse(-1, "name:" + segment_meta.getName() + " is exists");
        }

        try {
            tSegmentMetaRepository.save(segment_meta);
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @PutMapping("/{segment_id}")
    public MessageResponse updateSegmentMetaEntity(@PathVariable("segment_id") String segment_id, @RequestBody TSegmentMetaEntity segment_meta) {
        if (null == segment_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TSegmentMetaEntity> item = tSegmentMetaRepository.findById(segment_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + segment_id + " is not exists");
        }

        try {
            segment_meta.setUpdateTime(System.currentTimeMillis());
            tSegmentMetaRepository.save(item.get().merge(segment_meta));
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @DeleteMapping("/{segment_id}")
    public MessageResponse deleteSegmentMetaEntity(@PathVariable("segment_id") String segment_id) {
        if (null == segment_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TSegmentMetaEntity> item = tSegmentMetaRepository.findById(segment_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + segment_id + " is not exists");
        }

        try {
            tSegmentMetaRepository.deleteById(segment_id);
        } catch (Exception e) {
            return new MessageResponse(-1, e.getMessage());
        }

        return new MessageResponse(0, "");
    }

    @GetMapping("/list")
    public GetListMessageResponse<TSegmentMetaEntity> getTSegmentMetaList(@RequestParam("page") Integer page,
                                                                  @RequestParam("size") Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updateTime"));

        Page<TSegmentMetaEntity> page_list = tSegmentMetaRepository.findAll(pageable);

        return new GetListMessageResponse<TSegmentMetaEntity>(0, "", page, size, page_list.getTotalElements(), page_list.getContent());
    }

    @GetMapping("/{segment_id}/customers")
    public GetListMessageResponse<TCustomerEntity> GetCustomersBySegmentId(@PathVariable("segment_id") String segment_id,
                                                                       @RequestParam("page") Integer page,
                                                                       @RequestParam("size") Integer size) {
        Optional<TSegmentMetaEntity> item = tSegmentMetaRepository.findById(segment_id);
        if (!item.isPresent()) {
            //return new GetListMessageResponse<TCustomerEntity>(-1, "segment id:" + segment_id + " is not exists", page, size, new Long(0), null);
        }
/*
        List<String> tag_ids = getTagIdsBySegId(null);

        System.out.print(" **************************** count:" + tTagRepository.getTagCustomerCountByIds(tag_ids) + "\r\n");

        List<BigInteger> page_raw_customer_ids = tTagRepository.getTagCustomerIdsByTagIds(tag_ids, );


        return new GetListMessageResponse<TCustomerEntity>(0, "", page, size, new Long(0), null);
*/
        List<String> tag_ids = getTagIdsBySegId(null);

        Pageable pageable = PageRequest.of(page, size, Sort.by("customer_id"));

        Page<BigInteger> page_raw_customer_ids = tTagRepository.getTagCustomerIdsByTagIds(tag_ids, pageable);

        int customer_size = page_raw_customer_ids.getContent().size();
        List<Long> customer_ids = new ArrayList<>();
        for (int i = 0 ; i < customer_size; ++i) {
            customer_ids.add(page_raw_customer_ids.getContent().get(i).longValue());
        }

        List<TCustomerEntity> customers = tCustomerRepository.findByIdInOrderById(customer_ids);

        return new GetListMessageResponse<TCustomerEntity>(0, "", page, size, page_raw_customer_ids.getTotalElements(), customers);
    }

    private List<String> getTagIdsBySegId(TSegmentMetaEntity segment) {
        List<String> result = new ArrayList<>();
        result.add("f7da55f2-50aa-435e-9852-8226fa1b1493");
        result.add("e2fef9f7-f487-4751-859f-6af6a265dcd1");
        result.add("c26d2450-4b0c-4f86-b6a4-9cb7e89fafe5");
        result.add("ad77d687-e1ee-4c2f-8ed0-d78d2390cc0e");
        result.add("ac4d68ff-446d-4316-81b0-94e485e3b020");

        return result;
    }
}
