package com.wanwh.api.core.result;

import lombok.Data;

@Data
public class GlobalResult<T> {
    private boolean success = false;
    private T data;
    private T rows;
    private long total;
    private int code;
    private String message;

    public GlobalResult() {
    }

    public static  <T> GlobalResult<T> newInstance() {
        return new GlobalResult();
    }

}
