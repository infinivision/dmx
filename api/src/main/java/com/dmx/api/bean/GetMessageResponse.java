package com.dmx.api.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter @Getter
public class GetMessageResponse<T> extends MessageResponse {
    private T data;

    public GetMessageResponse(Integer code, String msg, T data) {
        this.setCode(code);
        this.setMsg(msg);

        this.setData(data);
    }
}
