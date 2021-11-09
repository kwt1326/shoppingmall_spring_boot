package com.kwtproject.shoppingmall.vo.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class UserInfoVo implements Serializable {
    private String username;
    private String realname;
    private String contact;
    private String email;
    private String role;
    private long userId;
}
