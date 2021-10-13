package com.kwtproject.shoppingmall.service;

import com.kwtproject.shoppingmall.domain.ProductEntity;
import com.kwtproject.shoppingmall.domain.QProductEntity;
import com.kwtproject.shoppingmall.domain.UserEntity;
import com.kwtproject.shoppingmall.dto.product.RequestAddProduct;
import com.kwtproject.shoppingmall.dto.product.RequestProductList;
import com.kwtproject.shoppingmall.repository.product.IProductRepository;
import com.kwtproject.shoppingmall.repository.user.IUserRepository;
import com.querydsl.core.BooleanBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class ProductService implements IProductService {
    @Autowired
    IProductRepository productRepository;

    @Autowired
    IUserRepository userRepository;

    @Override
    public Page<ProductEntity> getProductList(RequestProductList dto) {
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QProductEntity entity = QProductEntity.productEntity;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> userEntity = userRepository.findByUserName((String)authentication.getPrincipal());

        if (userEntity.isPresent()) {
            UserEntity user = userEntity.get();
            booleanBuilder.and(entity.user.id.eq(user.getId()));
        }

        if (dto.getCategory() != null) {
            booleanBuilder.and(entity.category.eq(dto.getCategory()));
        }
        if (dto.getName() != null) {
            booleanBuilder.and(entity.name.eq(dto.getName()));
        }

        Page<ProductEntity> result = productRepository.findAll(
                booleanBuilder,
                PageRequest.of(dto.getPage() - 1, 20, Sort.by("id"))
        );

        return result;
    }

    @Override
    public Optional<ProductEntity> getProductDetail(int id) {
        return Optional.empty();
    }

    @Override
    public ProductEntity addProduct(RequestAddProduct dto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<UserEntity> userEntity = userRepository.findByUserName((String)authentication.getPrincipal());

        if (userEntity.isPresent()) {
            ProductEntity entity = new ProductEntity(
                    userEntity.get(),
                    dto.getName(),
                    dto.getCategory(),
                    dto.getStock(),
                    dto.getPrice(),
                    dto.getDiscount(),
                    dto.isSaleable(),
                    dto.getProductImgSlug(),
                    dto.getProductModelSlug()
            );
            ProductEntity result = productRepository.save(entity);

            return result;
        }
        return null;
    }

    @Override
    public boolean deleteProduct() throws Exception {
        return false;
    }
}
