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

        Sort sort = Sort.by("id").descending();

        // 본인이 등록한 상품 리스트 검색 조건
        if (dto.isPublic() == false) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            Optional<UserEntity> userEntity = userRepository.findByUserName((String)authentication.getPrincipal());

            if (userEntity.isPresent()) {
                UserEntity user = userEntity.get();
                booleanBuilder.and(entity.user.id.eq(user.getId()));
            }
        }

        if (dto.getCategory() != null) {
            booleanBuilder.and(entity.category.eq(dto.getCategory()));
        }
        if (dto.getName() != null) {
            booleanBuilder.and(entity.name.eq(dto.getName()));
        }
        if (dto.isPopular() == true) {
            sort = sort.and(Sort.by("heart").descending());
        }

        Page<ProductEntity> result = productRepository.findAll(
                booleanBuilder,
                PageRequest.of(dto.getPage() - 1, 20, sort)
        );

        return result;
    }

    @Override
    public ProductEntity getProductDetail(Long id) {
        Optional<ProductEntity> findIt = productRepository.findById(id);
        if (findIt.isPresent()) {
            return findIt.get();
        }
        return null;
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
                    dto.getProductDetailImgSlug(),
                    dto.getProductModelSlug()
            );
            ProductEntity result = productRepository.save(entity);

            return result;
        }
        return null;
    }

    @Override
    public ProductEntity modifyProduct(RequestAddProduct dto, String id) {
        if (id != null) {
            Optional<ProductEntity> optional = productRepository.findById(Long.parseLong(id));

            if (optional.isPresent()) {
                ProductEntity entity = optional.get();

                entity.setName(dto.getName() != null ? dto.getName() : "");
                entity.setCategory(dto.getCategory());
                entity.setStock(dto.getStock());
                entity.setPrice(dto.getPrice());
                entity.setDiscount(dto.getDiscount());
                entity.setProductImgSlug(dto.getProductImgSlug() != null ? dto.getProductImgSlug() : "");
                entity.setProductDetailImgSlug(dto.getProductDetailImgSlug() != null ? dto.getProductDetailImgSlug() : "");
                entity.setProductModelSlug(dto.getProductModelSlug() != null ? dto.getProductModelSlug() : "");
                entity.set_saleable(dto.isSaleable());

                productRepository.save(entity);

                return entity;
            }
        }
        return null;
    }

    @Override
    public boolean deleteProduct() throws Exception {
        return false;
    }
}
