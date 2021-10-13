package com.kwtproject.shoppingmall.product;

import com.kwtproject.shoppingmall.domain.ProductEntity;
import com.kwtproject.shoppingmall.domain.QProductEntity;
import com.kwtproject.shoppingmall.domain.UserEntity;
import com.kwtproject.shoppingmall.repository.product.IProductRepository;
import com.kwtproject.shoppingmall.repository.user.IUserRepository;
import com.kwtproject.shoppingmall.testUtils.CreateTestEntity;
import com.querydsl.core.BooleanBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class ProductRepositoryTest {
    @Autowired
    private IProductRepository repository;

    @Autowired
    private IUserRepository userRepository;

    @Test
    @Order(1)
    public void save() {
        CreateTestEntity createUtil = new CreateTestEntity();

        UserEntity userEntity = createUtil.createUser(userRepository);

        ProductEntity entity = new ProductEntity(
                userEntity,
                "test_product",
                1,
                100,
                30000,
                10,
                true,
                "",
                ""
        );

        repository.save(entity);

        Optional<ProductEntity> findit = repository.findById(entity.getId());

        Assertions.assertEquals(entity, findit.get());
    }

    @Test
    @Order(2)
    public void getListQuery() {
        CreateTestEntity createUtil = new CreateTestEntity();

        UserEntity userEntity = createUtil.createUser(userRepository);

        List<ProductEntity> list = new ArrayList<>();

        for (int i = 0 ; i < 6 ; ++i) {
            ProductEntity entity = new ProductEntity(
                    userEntity,
                    "test_product_" + Integer.toString(i),
                     1,
                    100,
                    30000,
                    10,
                    true,
                    "",
                    ""
            );

            list.add(entity);
        }

        repository.saveAll(list);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QProductEntity entity = QProductEntity.productEntity;

        booleanBuilder.and(entity.category.eq(1));
        booleanBuilder.and(entity.name.contains("test_product"));

        Page<ProductEntity> result = repository.findAll(
                booleanBuilder,
                PageRequest.of(0, 5, Sort.by("id"))
        );

        List<ProductEntity> contents = result.getContent();

        Assertions.assertEquals(5, contents.size());

        for (ProductEntity content : contents) {
            System.out.println(content.getName());
            System.out.println(content.getId());
        }
    }
}
