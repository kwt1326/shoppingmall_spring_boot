package com.kwtproject.shoppingmall.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "products")
public class ProductEntity extends EntityBase {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int category;

    @Column(nullable = false)
    private int stock = 0;

    @Column(nullable = false)
    private int price = 0;

    @Column(nullable = false)
    private int heart = 0;

    @Column(nullable = false)
    private float discount = 0;

    @Column(nullable = false, name = "img_slug")
    private String productImgSlug;

    @Column(nullable = false)
    private boolean is_saleable = true;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "publish_at", nullable = true)
    private LocalDateTime publishAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "stop_at", nullable = true)
    private LocalDateTime stopAt;
}
