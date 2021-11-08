package com.kwtproject.shoppingmall.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "cart_item")
public class CartItemEntity extends EntityBase {
    public CartItemEntity(
            short quantity,
            ProductEntity productEntity,
            CartEntity cartEntity
    ) {
        this.quantity = quantity;
        this.product = productEntity;
        this.cart = cartEntity;
    }

    // 수량
    private short quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private CartEntity cart;
}
