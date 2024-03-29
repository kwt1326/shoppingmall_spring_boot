package com.kwtproject.shoppingmall.service;

import com.kwtproject.shoppingmall.domain.UserEntity;
import com.kwtproject.shoppingmall.dto.user.RequestSignIn;
import com.kwtproject.shoppingmall.dto.user.RequestSignUp;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

public interface IUserService extends UserDetailsService {
    String signUp(RequestSignUp requestSignUp) throws Exception;

    List<UserEntity> findUsers();

    Optional<UserEntity> findOne(Long userId);
}
