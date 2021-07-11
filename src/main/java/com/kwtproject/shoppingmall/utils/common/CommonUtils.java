package com.kwtproject.shoppingmall.utils.common;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CommonUtils {

    public boolean isEmpty(String str) {
        if (str != null) {
            return str.length() == 0;
        }
        return true;
    }

    public boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}
