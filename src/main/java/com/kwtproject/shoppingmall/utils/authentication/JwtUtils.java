package com.kwtproject.shoppingmall.utils.authentication;

import com.kwtproject.shoppingmall.domain.User;
import com.kwtproject.shoppingmall.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Component
public class JwtUtils {
    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    @Value("${spring.jwt.expireHour}")
    private int expireHour;

    private long tokenValidTime = 1000L * 60 * 60 * expireHour;

    public Optional<User> parseToken(String token) {
        UserRepository userRepository = new UserRepository();
        Claims body = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
                .parseClaimsJws(token).getBody();
        String name = body.get("name", String.class);

        return userRepository.findByName(name);
    }

    public String createToken(String userPk, Collection<? extends GrantedAuthority> roles) {
        Claims claims = Jwts.claims().setSubject(userPk);
        claims.put("roles", roles);
        Date now = new Date();
        Date expiration = new Date(now.getTime() + tokenValidTime);
        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
