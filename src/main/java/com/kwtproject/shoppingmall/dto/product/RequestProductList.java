package com.kwtproject.shoppingmall.dto.product;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RequestProductList {
    private String name;

    private Integer category;

    @Builder.Default
    private boolean popular = false;

    @Builder.Default
    private boolean isPublic = true;

    @Builder.Default
    private int page = 1;
}
