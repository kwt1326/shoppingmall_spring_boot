package com.kwtproject.shoppingmall.vo.cart;

import com.kwtproject.shoppingmall.domain.CartItemEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@Getter
public class ResponseCartItems implements Serializable {
    List<CartItemEntity> list;
}
