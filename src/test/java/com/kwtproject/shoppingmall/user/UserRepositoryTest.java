package com.kwtproject.shoppingmall.user;

import com.kwtproject.shoppingmall.domain.UserEntity;
import com.kwtproject.shoppingmall.repository.user.JpaUserRepository;
import com.kwtproject.shoppingmall.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest(properties = "spring.config.location=" + "classpath:/application-test.yml")
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JpaUserRepository repository;

    @Test
    public void save() {
        UserEntity user = new UserEntity();
        user.setUsername("tester");
        user.setName("wontae Kim");
        user.setPassword("qwer1234!");
        user.setUserContact("01012345678");
        user.setEmail("email@naver.com");
        user.setRole("");

        repository.save(user);

        UserEntity result = repository.findByUserName(user.getUsername()).get();
        Assertions.assertEquals(user, result);
    }
}
