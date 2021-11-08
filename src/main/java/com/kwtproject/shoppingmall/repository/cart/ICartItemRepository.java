package com.kwtproject.shoppingmall.repository.cart;

import com.kwtproject.shoppingmall.domain.CartItemEntity;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICartItemRepository extends CrudRepository<CartItemEntity, Long>, QuerydslPredicateExecutor<CartItemEntity> {
}
