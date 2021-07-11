package com.kwtproject.shoppingmall.security.domain;

import com.kwtproject.shoppingmall.domain.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.lang.reflect.Member;
import java.util.Collection;
import java.util.stream.Collectors;

/* userEntity 와는 별개로 security filter 를 거치기위한 유저 객체 */
public class CustomUser extends User {

    private static final long serialVersionUID = 1L;

    private UserEntity userEntity;

    public CustomUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public CustomUser(UserEntity userEntity) {
        super(userEntity.getUsername(),
                userEntity.getPassword(),
                userEntity.getAuthList()
                        .stream()
                        .map(auth -> new SimpleGrantedAuthority(auth.getAuthName()))
                        .collect(Collectors.toList()));

        this.userEntity = userEntity;
    }

    public UserEntity getUserEntity() {
        return this.userEntity;
    }
}
