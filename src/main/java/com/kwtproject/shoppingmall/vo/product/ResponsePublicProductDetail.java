package com.kwtproject.shoppingmall.vo.product;

import com.kwtproject.shoppingmall.domain.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
public class ResponsePublicProductDetail implements Serializable {
    private String name;
    private int category;
    private int stock;
    private int price;
    private int heart;
    private float discount;
    private boolean isSaleable;
    private String productImgSlug;
    private String productDetailImgSlug;
    private String productModelSlug;
}
