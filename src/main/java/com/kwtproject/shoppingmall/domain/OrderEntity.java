package com.kwtproject.shoppingmall.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "orders")
public class OrderEntity extends EntityBase {

//    private String token;

    private String state;

    private String address;

    // 총 금액 계산식
    // total_price - (total_price * discount) + shipping_charge
    private int total;

    // 총 할인액
    private int discount;

    // 주문번호
    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "content")
    private String contentInfo;

    @Column(name = "total_price")
    private int productTotalPrice;

    @Column(name = "shipping_charge")
    private int shippingCharge;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(referencedColumnName = "id")
    private UserEntity user;
}
