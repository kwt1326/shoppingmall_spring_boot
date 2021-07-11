package com.kwtproject.shoppingmall.user;

import com.kwtproject.shoppingmall.domain.UserEntity;
import com.kwtproject.shoppingmall.repository.user.JpaUserRepository;
import com.kwtproject.shoppingmall.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JpaUserRepository repository;

    @Test
    public void save() {
        UserEntity user = UserEntity.builder()
                .username("tester")
                .name("wontae Kim")
                .password("1234")
                .email("email@naver.com")
                .role("")
                .build();

        repository.save(user);

        UserEntity result = repository.findByUserName(user.getUsername()).get();
        Assertions.assertEquals(user, result);
    }
}
