package com.kwtproject.shoppingmall.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.List;

@Getter
@Entity
@Table(name = "product_category")
public class ProductCategoryEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // self-refer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_category_id")
    private ProductCategoryEntity parent;

    // self-refer
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<ProductCategoryEntity> children;

    private String name;
}
