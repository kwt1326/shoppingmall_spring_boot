package com.kwtproject.shoppingmall.vo.user;

import java.io.Serializable;

public class ResponseSignUp implements Serializable {
    private final String result;

    public ResponseSignUp(String result) {
        this.result = result;
    }

    public String getResult() { return this.result; }
}
