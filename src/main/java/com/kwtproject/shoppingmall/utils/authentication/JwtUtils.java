package com.kwtproject.shoppingmall.utils.authentication;

import com.kwtproject.shoppingmall.domain.UserEntity;
import com.kwtproject.shoppingmall.repository.user.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.*;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    @Value("${spring.jwt.expireHour}")
    private int expireHour;

    private long tokenValidTime = 1000L * 60 * 60 * expireHour;

//    public Optional<UserEntity> parseToken(String token) {
//        UserRepository userRepository = new UserRepository();
//        Claims body = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
//                .parseClaimsJws(token).getBody();
//        String name = body.get("name", String.class);
//
//        return userRepository.findByName(name);
//    }
//
//    public String createToken(String username) {
//        Map<String, Object> claims = new HashMap<>();
//        Date now = new Date();
//        Date expiration = new Date(now.getTime() + tokenValidTime);
//        SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(username)
//                .setIssuedAt(now)
//                .setExpiration(expiration)
//                .signWith(secretKey, SignatureAlgorithm.HS256)
//                .compact();
//    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
