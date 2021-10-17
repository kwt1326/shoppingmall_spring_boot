package com.kwtproject.shoppingmall.service;

import com.kwtproject.shoppingmall.domain.ProductEntity;
import com.kwtproject.shoppingmall.dto.product.RequestAddProduct;
import com.kwtproject.shoppingmall.dto.product.RequestProductList;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface IProductService {
    Page<ProductEntity> getProductList(RequestProductList dto);

    Optional<ProductEntity> getProductDetail(int id);

    ProductEntity addProduct(RequestAddProduct dto) throws Exception;

    ProductEntity modifyProduct(RequestAddProduct dto, String id) throws Exception;

    boolean deleteProduct() throws Exception;
}
