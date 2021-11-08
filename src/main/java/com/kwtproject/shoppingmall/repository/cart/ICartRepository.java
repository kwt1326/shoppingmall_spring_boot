package com.kwtproject.shoppingmall.repository.cart;

import com.kwtproject.shoppingmall.domain.CartEntity;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartRepository extends CrudRepository<CartEntity, Long>, QuerydslPredicateExecutor<CartEntity> {
}
