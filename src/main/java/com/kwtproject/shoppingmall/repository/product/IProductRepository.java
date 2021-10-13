package com.kwtproject.shoppingmall.repository.product;

import com.kwtproject.shoppingmall.domain.ProductEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends CrudRepository<ProductEntity, Long>, QuerydslPredicateExecutor<ProductEntity> {

}
