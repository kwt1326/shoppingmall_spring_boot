package com.kwtproject.shoppingmall.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "cart")
public class CartEntity extends EntityBase {

    // 각 토큰별 카트 식별 (redis 사용 권장)
    private String token;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private UserEntity user;
}
