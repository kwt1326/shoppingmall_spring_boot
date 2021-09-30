package com.kwtproject.shoppingmall.service;

import com.kwtproject.shoppingmall.domain.ProductEntity;
import com.kwtproject.shoppingmall.dto.product.RequestAddProduct;
import com.kwtproject.shoppingmall.dto.product.RequestProductList;
import com.kwtproject.shoppingmall.repository.product.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService implements IProductService {
    @Autowired
    IProductRepository productRepository;

    @Override
    public Page<ProductEntity> getProductList(RequestProductList dto) {
        return productRepository.findFilterAll(
                dto.getCategory(),
                dto.getName(),
                PageRequest.of(dto.getPage(), 20, Sort.by("id"))
                );
    }

    @Override
    public Optional<ProductEntity> getProductDetail(int id) {
        return Optional.empty();
    }

    @Override
    public ProductEntity addProduct(RequestAddProduct dto) throws Exception {
        ProductEntity entity = new ProductEntity(
                dto.getName(),
                dto.getCategory(),
                dto.getStock(),
                dto.getPrice(),
                0, true,
                dto.getProductImgSlug(),
                dto.getProductModelSlug()
        );
        ProductEntity result = productRepository.save(entity);
        System.out.print("TEST SAVE : ");
        System.out.print(result);
        return result;
    }

    @Override
    public boolean deleteProduct() throws Exception {
        return false;
    }
}
