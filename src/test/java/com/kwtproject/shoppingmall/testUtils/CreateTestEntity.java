package com.kwtproject.shoppingmall.testUtils;

import com.kwtproject.shoppingmall.domain.ProductEntity;
import com.kwtproject.shoppingmall.domain.UserEntity;
import com.kwtproject.shoppingmall.repository.product.IProductRepository;
import com.kwtproject.shoppingmall.repository.user.IUserRepository;

public class CreateTestEntity {
    public CreateTestEntity() {}

    public UserEntity createUser(IUserRepository repository) {
        UserEntity userEntity = new UserEntity();

        userEntity.setUsername("tester");
        userEntity.setName("wontae Kim");
        userEntity.setPassword("qwer1234!");
        userEntity.setUserContact("01012345678");
        userEntity.setEmail("email@naver.com");
        userEntity.setRole("");

        repository.save(userEntity);

        return userEntity;
    }

    public ProductEntity createProduct(IProductRepository repository, IUserRepository userRepository) {
        UserEntity userEntity = this.createUser(userRepository);

        ProductEntity productEntity = new ProductEntity(
                userEntity,
                "test_product",
                1,
                100,
                30000,
                10,
                true,
                "",
                "",
                ""
        );

        repository.save(productEntity);

        return productEntity;
    }
}
