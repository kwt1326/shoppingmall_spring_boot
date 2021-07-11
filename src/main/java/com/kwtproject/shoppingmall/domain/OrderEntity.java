package com.kwtproject.shoppingmall.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Table(name = "order")
public class OrderEntity extends EntityBase {

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
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
