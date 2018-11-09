package com.dmx.api.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
public class GetListMessageResponse<T> extends MessageResponse {
    private Integer page;
    private Integer size;
    private Long total;

    private List<T> data;

    public GetListMessageResponse(Integer code, String msg, Integer page, Integer size, Long total, List<T> data) {
        this.page = page;
        this.size = size;
        this.total = total;

        this.setCode(code);
        this.setMsg(msg);

        this.setData(data);
    }
}
