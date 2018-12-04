package com.dmx.api.controller.meta;

import com.dmx.api.bean.GetListNoPageMessageResponse;
import com.dmx.api.bean.GetMessageResponse;
import com.dmx.api.bean.MessageResponse;
import com.dmx.api.dao.meta.TColumnMetaRepository;
import com.dmx.api.entity.meta.TCategoryEntity;
import com.dmx.api.entity.meta.TColumnMetaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/column")
public class TColumnMetaController {
    @Autowired
    TColumnMetaRepository tColumnMetaRepository;

    @GetMapping("/{column_id}")
    public GetMessageResponse<TColumnMetaEntity> getColumnEntity(@PathVariable("column_id") String column_id) {
        Optional<TColumnMetaEntity> item = tColumnMetaRepository.findById(column_id);
        if (item.isPresent()) {
            return new GetMessageResponse<TColumnMetaEntity>(0, "", item.get());
        }

        return new GetMessageResponse<TColumnMetaEntity>(-1, "has no record:" + column_id, null);
    }

    @PostMapping("")
    public MessageResponse setColumnMetaEntity(@Validated @RequestBody TColumnMetaEntity column, BindingResult check) {
        if (check.hasErrors()) {
            return new MessageResponse(-1, check.getAllErrors().get(0).getDefaultMessage());
        }

        TColumnMetaEntity item = tColumnMetaRepository.findByColumnName(column.getColumnName());
        if (null != item) {
            return new MessageResponse(-1, "name:" + column.getColumnName() + " is exists");
        }

        column.setCreateUserName("admin");
        column.setCreateGroupName("group");
        column.setPlatformId(1);
        column.setPlatformName("infinivision");


        tColumnMetaRepository.save(column);

        return new MessageResponse(0, "");
    }

    @DeleteMapping("/{column_id}")
    public MessageResponse deleteCategoryEntity(@PathVariable("column_id") String column_id) {
        if (null == column_id) {
            return new MessageResponse(-1, "id must not be null");
        }

        Optional<TColumnMetaEntity> item = tColumnMetaRepository.findById(column_id);
        if (!item.isPresent()) {
            return new MessageResponse(0, "id:" + column_id + " is not exists");
        }

        tColumnMetaRepository.deleteById(column_id);

        return new MessageResponse(0, "");
    }

    @GetMapping("/category")
    public GetListNoPageMessageResponse<String> getColumnCategoryList(@RequestParam(value = "flag", defaultValue = "0", required = false) Integer flag) {
        List<String> list = tColumnMetaRepository.getCategoryByCalculateFlag(flag);

        return new GetListNoPageMessageResponse<String>(0, "", list);
    }

    @GetMapping("/query_by_category")
    public GetListNoPageMessageResponse<TColumnMetaEntity> getColumnByTableList(@RequestParam(value = "category", defaultValue = "") String category,
                                                                                @RequestParam(value = "flag", defaultValue = "0", required = false) Integer flag) {
        List<TColumnMetaEntity> list = tColumnMetaRepository.findByCategoryAndCalculateFlag(category, flag);

        return new GetListNoPageMessageResponse<TColumnMetaEntity>(0, "", list);
    }
}
