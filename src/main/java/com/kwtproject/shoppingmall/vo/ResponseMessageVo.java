package com.kwtproject.shoppingmall.vo;

import java.io.Serializable;

public class ResponseMessageVo implements Serializable {
    public ResponseMessageVo(String msg, int code) {
        this.message = msg;
        this.status_code = code;
    }

    private final String message;

    private final int status_code;
}
