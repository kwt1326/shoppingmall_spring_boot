package com.kwtproject.shoppingmall.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "cart_item")
public class CartItemEntity extends EntityBase {

    // 계산된 최종 가격 ( discount & quantity )
    private int price;

    // 할인액
    private int discount;

    // 수량
    private short quantity;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private CartEntity cart;
}
