package com.kwtproject.shoppingmall.dto.user;

import lombok.Getter;

@Getter
public class RequestSignUp {
    private String username;
    private String name;
    private String password;
    private String email;
}
