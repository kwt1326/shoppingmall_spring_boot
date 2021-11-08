package com.kwtproject.shoppingmall.service;

import com.kwtproject.shoppingmall.domain.*;
import com.kwtproject.shoppingmall.dto.cart.RequestAddCartItem;
import com.kwtproject.shoppingmall.repository.cart.ICartItemRepository;
import com.kwtproject.shoppingmall.repository.cart.ICartRepository;
import com.kwtproject.shoppingmall.repository.product.IProductRepository;
import com.kwtproject.shoppingmall.utils.common.AuthUtils;
import com.querydsl.core.BooleanBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class CartService implements ICartService {
    @Autowired
    private ICartRepository repository;

    @Autowired
    private ICartItemRepository itemRepository;

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private AuthUtils authUtils;

    // 장바구니 항목 리스트 가져오
    @Override
    public Page<CartItemEntity> getCartItems(WebRequest request, int page) throws Exception {
        CartEntity cartEntity = this.getCart(request);

        if (cartEntity != null) {
            QCartItemEntity itemEntity = QCartItemEntity.cartItemEntity;

            BooleanBuilder booleanBuilder = new BooleanBuilder();
            booleanBuilder.and(itemEntity.cart.id.eq(cartEntity.getId()));

            Sort sort = Sort.by("id").descending();

            Page<CartItemEntity> result = itemRepository.findAll(
                    booleanBuilder,
                    PageRequest.of(page - 1, 10, sort)
            );
            return result;
        }
        return null;
    }

    // 장바구니 담기 (장바구니가 없으면 생성 후 담는다.)
    @Override
    public CartItemEntity addCartItem(WebRequest request, RequestAddCartItem item) throws Exception {
        CartEntity cartEntity = this.getCart(request);

        if (cartEntity == null) {
            String token = authUtils.getToken(request);
            UserEntity userEntity = authUtils.getLoggedUser(request);

            if (userEntity != null) {
                CartEntity newCartEntity = new CartEntity(token, userEntity);
                cartEntity = repository.save(newCartEntity);
            }
        }

        Optional<ProductEntity> findIt = productRepository.findById(item.getProductId());

        if (findIt.isPresent()) {
            CartItemEntity itemEntity = new CartItemEntity(
                    item.getQuantity(),
                    findIt.get(),
                    cartEntity
            );
            CartItemEntity resultEntity = itemRepository.save(itemEntity);

            return resultEntity;
        }
        return null;
    }

    // 장바구니 가져오기 (현재 유저의 접속 세션(토큰 존재하는))
    @Override
    public CartEntity getCart(WebRequest request) throws Exception {
        String token = authUtils.getToken(request);
        UserEntity userEntity = authUtils.getLoggedUser(request);
        if (userEntity != null) {
            Long loggedUserId = userEntity.getId();

            if (token != null && loggedUserId != null) {
                QCartEntity entity = QCartEntity.cartEntity;

                BooleanBuilder booleanBuilder = new BooleanBuilder();

                booleanBuilder.and(entity.token.eq(token));
                booleanBuilder.and(entity.user.id.eq(loggedUserId));

                Optional<CartEntity> findIt = repository.findOne(booleanBuilder);

                if (findIt.isPresent()) {
                    return findIt.get();
                }
            }
        }
        return null;
    }

    // 카트 비우기
    @Override
    public boolean clearCart(WebRequest request) throws Exception {
        CartEntity cartEntity = this.getCart(request);

        if (cartEntity == null) {
            QCartItemEntity entity = QCartItemEntity.cartItemEntity;

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            booleanBuilder.and(entity.cart.id.eq(cartEntity.getId()));

            Iterable<CartItemEntity> findList = itemRepository.findAll(booleanBuilder);

            itemRepository.deleteAll(findList);

            return true;
        }
        return false;
    }

    // 선택한 장바구니 항목 지우기
    @Override
    public boolean deleteCartItem(WebRequest request, long id) throws Exception {
        CartEntity cartEntity = this.getCart(request);

        if (cartEntity == null) {
            QCartItemEntity entity = QCartItemEntity.cartItemEntity;

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            booleanBuilder.and(entity.id.eq(id));

            Optional<CartItemEntity> findIt = itemRepository.findOne(booleanBuilder);

            if (findIt.isPresent()) {
                itemRepository.delete(findIt.get());
                return true;
            }
        }
        return false;
    }
}
