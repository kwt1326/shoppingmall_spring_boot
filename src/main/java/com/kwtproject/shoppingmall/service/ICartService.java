package com.kwtproject.shoppingmall.service;

import com.kwtproject.shoppingmall.domain.CartEntity;
import com.kwtproject.shoppingmall.domain.CartItemEntity;
import com.kwtproject.shoppingmall.dto.cart.RequestAddCartItem;
import org.springframework.data.domain.Page;
import org.springframework.web.context.request.WebRequest;

public interface ICartService {
    CartEntity getCart(WebRequest request) throws Exception;

    CartItemEntity addCartItem(WebRequest request, RequestAddCartItem item) throws Exception;

    Page<CartItemEntity> getCartItems(WebRequest request, int page) throws Exception;

    boolean deleteCartItem(WebRequest request, long id) throws Exception;

    boolean clearCart(WebRequest request) throws Exception;
}
