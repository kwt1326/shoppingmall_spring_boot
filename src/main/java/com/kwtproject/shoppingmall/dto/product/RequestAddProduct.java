package com.kwtproject.shoppingmall.dto.product;

import lombok.Builder;
import lombok.Getter;

@Getter
public class RequestAddProduct {
    private String name;

    private int category = 0;

    private int stock = 0;

    private int price = 0;

    private int heart = 0;

    private float discount = 0;

    private boolean isSaleable = true;

    // nullable
    private String productImgSlug;

    // nullable
    private String productModelSlug;
}
