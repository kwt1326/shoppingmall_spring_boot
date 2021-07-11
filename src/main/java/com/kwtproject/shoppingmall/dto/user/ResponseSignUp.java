package com.kwtproject.shoppingmall.dto.user;

import java.io.Serializable;

public class ResponseSignUp implements Serializable {
    private final String jwt;

    public ResponseSignUp(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() { return this.jwt; }
}
