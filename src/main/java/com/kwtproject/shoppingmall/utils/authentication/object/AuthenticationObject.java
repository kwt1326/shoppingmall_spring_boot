package com.kwtproject.shoppingmall.utils.authentication.object;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class AuthenticationObject extends UsernamePasswordAuthenticationToken {
    public AuthenticationObject(String principal, String credentials) {
        super(principal, credentials);
    }
}
