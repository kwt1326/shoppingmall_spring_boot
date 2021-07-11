package com.kwtproject.shoppingmall.dto.user;

import lombok.Getter;


@Getter
public class RequestSignIn {
    public RequestSignIn(String username, String password) {
        this.username = username;
        this.password = password;
    }
    private String username;
    private String password;
}
