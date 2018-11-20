package com.dmx.api.service.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class SegmentRuleBean {
    private String logic; // 1:and 2:or
    private List<String> ids;

    public SegmentRuleBean(String logic, List<String> ids) {
        this.logic = logic;
        this.ids = ids;
    }
}
