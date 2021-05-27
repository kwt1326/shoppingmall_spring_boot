package com.kwtproject.shoppingmall.repository.user;

import com.kwtproject.shoppingmall.domain.UserEntity;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    UserEntity save(UserEntity user);
    Optional<UserEntity> findById(Long id);
    Optional<UserEntity> findByName(String name);
    Optional<UserEntity> findByUserName(String name);
    List<UserEntity> findAll();
}
