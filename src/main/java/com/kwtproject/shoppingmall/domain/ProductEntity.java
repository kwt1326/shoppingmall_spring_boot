package com.kwtproject.shoppingmall.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "products")
public class ProductEntity extends EntityBase {

    public ProductEntity(
            String name,
            int category,
            int stock,
            int price,
            float discount,
            boolean is_saleable,
            String productImgSlug,
            String productModelSlug
    ) {
        this.name = name;
        this.category = category;
        this.stock = stock;
        this.price = price;
        this.discount = discount;
        this.is_saleable = is_saleable;
        this.productImgSlug = productImgSlug;
        this.productModelSlug = productModelSlug;
    }

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int category = 0;

    @Column(nullable = false)
    private int stock = 0;

    @Column(nullable = false)
    private int price = 0;

    @Column(nullable = false)
    private int heart = 0;

    @Column(nullable = false)
    private float discount;

    @Column(nullable = true, name = "img_slug")
    private String productImgSlug;

    @Column(nullable = true, name = "model_slug")
    private String productModelSlug;

    @Column(nullable = false)
    private boolean is_saleable = true;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "publish_at", nullable = true)
    private LocalDateTime publishAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "stop_at", nullable = true)
    private LocalDateTime stopAt;
}
