package com.kwtproject.shoppingmall.utils.authentication.process;

import com.kwtproject.shoppingmall.domain.UserEntity;
import com.kwtproject.shoppingmall.repository.user.UserRepository;
import com.kwtproject.shoppingmall.utils.authentication.object.AuthenticationUserObject;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//@Service
//@RequiredArgsConstructor
public class RestAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (authentication.isAuthenticated()) {
            return authentication;
        }

        UserRepository userRepository = new UserRepository();
        Optional<UserEntity> user = userRepository.findByName((String)authentication.getPrincipal());
        if (user.isPresent()) {
            return new AuthenticationUserObject(user.get().getUsername());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return AuthenticationUserObject.class.isAssignableFrom(authentication);
    }
}
