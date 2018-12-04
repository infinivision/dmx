package com.dmx.api.controller.meta;

import com.dmx.api.bean.GetListNoPageMessageResponse;
import com.dmx.api.dao.meta.TOperatorMetaRepository;
import com.dmx.api.entity.meta.TOperatorMetaEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/operator")
public class TOperatorMetaController {
    @Autowired
    TOperatorMetaRepository tOperatorMetaRepository;

    @GetMapping("/list")
    public GetListNoPageMessageResponse<TOperatorMetaEntity> getRootCategoryList(@RequestParam(value = "type", defaultValue = "0", required = false) Integer type) {
        List<TOperatorMetaEntity> list = tOperatorMetaRepository.findByType(type);

        return new GetListNoPageMessageResponse<TOperatorMetaEntity>(0, "", list);
    }
}
