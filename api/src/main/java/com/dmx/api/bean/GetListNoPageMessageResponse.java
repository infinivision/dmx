package com.dmx.api.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
public class GetListNoPageMessageResponse<T> extends MessageResponse {
    private List<T> data;

    public GetListNoPageMessageResponse(Integer code, String msg, List<T> data) {
        this.setCode(code);
        this.setMsg(msg);

        this.setData(data);
    }
}
