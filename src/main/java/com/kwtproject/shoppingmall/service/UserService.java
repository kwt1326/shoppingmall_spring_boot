package com.kwtproject.shoppingmall.service;

import com.kwtproject.shoppingmall.domain.UserEntity;
import com.kwtproject.shoppingmall.dto.user.RequestSignUp;
import com.kwtproject.shoppingmall.repository.user.JpaUserRepository;
import com.kwtproject.shoppingmall.security.domain.CustomUser;
import com.kwtproject.shoppingmall.utils.authentication.JwtUtils;
import com.kwtproject.shoppingmall.utils.authentication.ValidateUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;

import javax.transaction.Transactional;
import java.security.InvalidParameterException;
import java.util.*;

@Service
@Transactional
public class UserService implements IUserService { // common + security service

    @Autowired
    private JpaUserRepository repository;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = repository.findByUserName(username);
        if (userEntity.isPresent()) {
            UserEntity userVo = userEntity.get();
            return new CustomUser(userVo);
        }
        return null;
    }

    /* 가입 (로그인 security 에 위임) */
    @Override
    public String signUp(RequestSignUp requestSignUp, String role) throws Exception {
        try {
            /* 패스워드 유효성 검사 (8-16자 영/숫자/특수문자 1자 이상) */
            if (new ValidateUtils().ValidatePassword(requestSignUp.getPassword()) == false) {
                throw new InvalidParameterException("invalid password");
            }

            /* 중복 회원 검사 */
            repository.findByUserName(requestSignUp.getUsername()).ifPresent(m -> {
                throw new IllegalStateException("이미 존재하는 회원입니다.");
            });

            UserEntity user = new UserEntity();
            user.setName(requestSignUp.getName());
            user.setPassword(new BCryptPasswordEncoder().encode(requestSignUp.getPassword()));
            user.setUsername(requestSignUp.getUsername());
            user.setUserContact(requestSignUp.getContact());
            user.setEmail(requestSignUp.getEmail());
            user.setRole(role);

            repository.save(user);

            /* 회원 가입 성공시 토큰 반환하여 바로 로그인 가능하도록 함 */
            return jwtUtils.generateToken(loadUserByUsername(user.getUsername()));
        } catch (Exception e) {
            throw new Exception("Throw Interval Server Error : " + e.getMessage());
        }
    }

    @Override
    public Boolean checkLogging(WebRequest request) {
        String token = request.getHeader("Authorization");
        String username = jwtUtils.extractUsername(token);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        System.out.println(username);
        System.out.println((String)authentication.getPrincipal());

        String authorizedUsername = (String)authentication.getPrincipal();

        return username.equals(authorizedUsername);
    }

    @Override
    public List<UserEntity> findUsers() {
        return repository.findAll();
    }

    @Override
    public Optional<UserEntity> findOne(Long userId) {
        return repository.findById(userId);
    }
}
