package com.kwtproject.shoppingmall.utils.authentication.object;

import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AuthenticationUserObject extends AbstractAuthenticationToken {
    private final String principal;

    public AuthenticationUserObject(String principal) {
        super(null);
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
