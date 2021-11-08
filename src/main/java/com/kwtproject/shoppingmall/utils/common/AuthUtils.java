package com.kwtproject.shoppingmall.utils.common;

import com.kwtproject.shoppingmall.domain.UserEntity;
import com.kwtproject.shoppingmall.repository.user.IUserRepository;
import com.kwtproject.shoppingmall.utils.authentication.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import java.util.Optional;

@Component
public class AuthUtils {
    @Autowired
    private IUserRepository repository;

    @Autowired
    private JwtUtils jwtUtils;

    public String getToken(WebRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null) {
            return token.substring(7);
        }
        return null;
    }

    public UserEntity getLoggedUser(WebRequest request) {
        String token = request.getHeader("Authorization");
        String username = jwtUtils.extractUsername(token);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String authorizedUsername = (String)authentication.getPrincipal();

        if (username.equals(authorizedUsername)) {
            Optional<UserEntity> entity = repository.findByName(username);
            if (entity.isPresent()) {
                return entity.get();
            }
        }
        return null;
    }
}
