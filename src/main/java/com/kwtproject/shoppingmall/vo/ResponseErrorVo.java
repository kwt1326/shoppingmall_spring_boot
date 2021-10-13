package com.kwtproject.shoppingmall.vo;

import java.io.Serializable;

public class ResponseErrorVo implements Serializable {
    public ResponseErrorVo(String msg, int code) {
        this.message = msg;
        this.error_code = code;
    }

    private final String message;

    private final int error_code;
}
