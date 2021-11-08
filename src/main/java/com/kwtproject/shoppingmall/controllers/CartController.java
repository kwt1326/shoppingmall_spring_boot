package com.kwtproject.shoppingmall.controllers;

import com.kwtproject.shoppingmall.domain.CartEntity;
import com.kwtproject.shoppingmall.domain.CartItemEntity;
import com.kwtproject.shoppingmall.dto.cart.RequestAddCartItem;
import com.kwtproject.shoppingmall.service.CartService;
import com.kwtproject.shoppingmall.vo.ResponseMessageVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

@PreAuthorize("hasRole('USER_COMMON')")
@Controller
@RequestMapping(value = "cart")
public class CartController {
    @Autowired
    private CartService service;

    @GetMapping("/{page}")
    private ResponseEntity<?> getCart(
            WebRequest request,
            @PathVariable String page
    ) throws Exception {
        Page<CartItemEntity> list = service.getCartItems(request, Integer.parseInt(page));

        if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }

        ResponseMessageVo errorVo = new ResponseMessageVo("Not found Cart", 400);
        return new ResponseEntity<>(errorVo, HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    private ResponseEntity<?> addCartItem(
            WebRequest request,
            @RequestBody RequestAddCartItem itemInfo
    ) throws Exception {
        CartItemEntity resultEntity = service.addCartItem(request, itemInfo);

        if (resultEntity != null) {
            ResponseMessageVo successVo = new ResponseMessageVo("Success", 200);
            return new ResponseEntity<>(successVo, HttpStatus.OK);
        }

        ResponseMessageVo errorVo = new ResponseMessageVo("failed Add Cart Item", 400);
        return new ResponseEntity<>(errorVo, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(value = "/{id}")
    private ResponseEntity<?> deleteCartItem(
            WebRequest request,
            @PathVariable String id
    ) throws Exception {
        boolean result = service.deleteCartItem(request, Long.parseLong(id));

        ResponseMessageVo resultVo = new ResponseMessageVo(
                result == true ? "Success" : "Failed",
                result == true ? 200 : 500
        );
        return new ResponseEntity<>(resultVo, result == true ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
