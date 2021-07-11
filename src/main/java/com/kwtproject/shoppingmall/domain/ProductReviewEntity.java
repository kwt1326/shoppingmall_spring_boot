package com.kwtproject.shoppingmall.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Table(name = "product_review")
public class ProductReviewEntity extends EntityBase {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_review_id")
    private ProductReviewEntity parent;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    private List<ProductReviewEntity> reviews;

    private String content;

    @Column(name = "rating")
    private short starRating;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(name = "published_at")
    private LocalDateTime publishedAt;
}
