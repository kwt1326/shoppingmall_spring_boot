package com.kwtproject.shoppingmall.utils.authentication;

import com.kwtproject.shoppingmall.constant.RegexPatterns;

import java.util.regex.Pattern;

public class ValidateUtils {
    public boolean ValidatePassword(final String password) {
        return Pattern.matches(new RegexPatterns().getPasswordRegex(), password);
    }
}
