package com.kwtproject.shoppingmall.utils.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
public class ConfigurationPropertiesProvider {

    @Value("${custom.jwt.secret}")
    private String secret;

    @Value("${custom.jwt.expireHour}")
    private int expireHour;
}
