package com.kwtproject.shoppingmall.constant;

import lombok.Getter;

@Getter
public class RegexPatterns {
    private final String passwordRegex = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\\\)\\-_=+]).{8,16}$";
}
