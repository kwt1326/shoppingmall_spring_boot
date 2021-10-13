package com.kwtproject.shoppingmall.vo.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseProductListElement {
    private String name;
    private int category;
    private int stock;
    private int price;
    private float discount;
    private boolean is_saleable;
    private String img_src;
    private String model_src;
    private long user_id;
}
