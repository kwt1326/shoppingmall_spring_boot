package com.kwtproject.shoppingmall.service;

import com.kwtproject.shoppingmall.domain.User;
import com.kwtproject.shoppingmall.repository.IUserRepository;
import com.kwtproject.shoppingmall.repository.JpaUserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private JpaUserRepository repository;

    public UserService(JpaUserRepository repository) {
        this.repository = repository;
    }

    /* 가입 */
    public Long join(User user) {
        /* 중복 회원 검사 */
        repository.findByName(user.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });

        repository.save(user);
        return user.getId();
    }

//    public Optional<User> signIn(User user) {
//        User user =
//    }

    public List<User> findUsers() {
        return repository.findAll();
    }

    public Optional<User> findOne(Long userId) {
        return repository.findById(userId);
    }
}
