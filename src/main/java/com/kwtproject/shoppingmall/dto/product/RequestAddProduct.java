package com.kwtproject.shoppingmall.dto.product;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestAddProduct {
    private String name;

    private int category;

    private int stock;

    private int price;

    private float discount;

    private boolean isSaleable;

    // nullable
    private String productImgSlug;

    // nullable
    private String productModelSlug;
}
