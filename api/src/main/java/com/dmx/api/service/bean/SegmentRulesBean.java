package com.dmx.api.service.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class SegmentRulesBean {
    private String logic; // 1:and 2:or
    private List<SegmentRuleBean> rules;

    public SegmentRulesBean(String logic, List<SegmentRuleBean> rules) {
        this.logic = logic;
        this.rules = rules;
    }
}
