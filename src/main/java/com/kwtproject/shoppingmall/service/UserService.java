package com.kwtproject.shoppingmall.service;

import com.kwtproject.shoppingmall.domain.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import com.kwtproject.shoppingmall.dto.user.RequestSignUp;
import com.kwtproject.shoppingmall.repository.user.JpaUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private JpaUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = repository.findByUserName(username);
        if (userEntity.isPresent()) {
            UserEntity userVo = userEntity.get();
            System.out.println(userVo.toString());
            return new User(
                    userVo.getUsername(),
                    userVo.getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority(userVo.getRole()))
            );
        }
        return null;
    }

    /* 가입 */
    public Long signUp(UserEntity user) {
        /* 중복 회원 검사 */
        repository.findByName(user.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

        repository.save(user);
        return user.getId();
    }

    public List<UserEntity> findUsers() {
        return repository.findAll();
    }

    public Optional<UserEntity> findOne(Long userId) {
        return repository.findById(userId);
    }
}
