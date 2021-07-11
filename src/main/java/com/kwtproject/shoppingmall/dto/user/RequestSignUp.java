package com.kwtproject.shoppingmall.dto.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestSignUp {
    private String username;
    private String name;
    private String password;
    private String email;
}
