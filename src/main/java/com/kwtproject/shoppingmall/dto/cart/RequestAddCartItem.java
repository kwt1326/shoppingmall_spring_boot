package com.kwtproject.shoppingmall.dto.cart;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RequestAddCartItem {
    private short quantity;
    private Long productId;
}
