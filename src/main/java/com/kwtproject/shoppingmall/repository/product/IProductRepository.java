package com.kwtproject.shoppingmall.repository.product;

import com.kwtproject.shoppingmall.domain.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<ProductEntity, Long> {
    @Query(value =
            "SELECT * FROM PRODUCTS AS m WHERE " +
                    "m.category = :category AND " +
                    "m.name LIKE CONCAT('%', :name, '%')",
            nativeQuery = true
    )
    Page<ProductEntity> findFilterAll(
            @Param("category") int category,
            @Param("name") String name,
            Pageable pageable
    );
}
