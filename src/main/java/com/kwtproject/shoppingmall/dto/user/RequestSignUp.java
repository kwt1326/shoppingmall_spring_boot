package com.kwtproject.shoppingmall.dto.user;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RequestSignUp {
    private String username;
    private String name;
    private String password;
    private String email;
    private String contact;
    private String role = "USER_COMMON";
}
