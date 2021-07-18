package com.kwtproject.shoppingmall.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "order_transaction")
public class OrderTransactionEntity extends EntityBase {

    private String state;

    // 결제 ID
    @Column(name = "payment_id")
    private String paymentId;

    // 대변/차변
    @Column(name = "order_type")
    private String orderType;

    // 주문 방법 (offline/online/착불/환어음 등)
    @Column(name = "order_method")
    private String orderMethod;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id")
    private OrderEntity order;
}
