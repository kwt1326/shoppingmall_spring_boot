package com.kwtproject.shoppingmall.repository;

import com.kwtproject.shoppingmall.domain.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    User save(User user);
    Optional<User> findById(Long id);
    Optional<User> findByName(String name);
    Optional<User> findByUserName(String name);
    List<User> findAll();
}
