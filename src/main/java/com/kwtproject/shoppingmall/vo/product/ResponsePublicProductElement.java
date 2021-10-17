package com.kwtproject.shoppingmall.vo.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponsePublicProductElement {
    private Long id;
    private String name;
    private int stock;
    private int price;
    private float discount;
    private int heart;
    private String img_src;
}
