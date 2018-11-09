package com.dmx.api.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter @Setter
public class MessageResponse {
    private Integer code;
    private String msg;

    public MessageResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
