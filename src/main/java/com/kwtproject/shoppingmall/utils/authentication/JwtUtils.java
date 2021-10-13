package com.kwtproject.shoppingmall.utils.authentication;

import com.kwtproject.shoppingmall.security.domain.CustomUser;
import com.kwtproject.shoppingmall.utils.common.ConfigurationPropertiesProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtUtils {

    @Value("${custom.jwt.secret}")
    private String SECRET_KEY;

    @Value("${custom.jwt.expireHour}")
    private int expireHour;

    public JwtUtils(ConfigurationPropertiesProvider config) {
        this.SECRET_KEY = config.getSecret();
        this.expireHour = config.getExpireHour();
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public List<SimpleGrantedAuthority> extractAuthorities(String token) {
        Optional<Claims> claims = extractAllClaims(token);
        if (claims.isPresent()) {
            return ((List<?>)claims.get().get("claim_role"))
                    .stream()
                    .map(authority -> new SimpleGrantedAuthority((String)authority))
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Optional<Claims> claims = extractAllClaims(token);
        if (claims.isPresent()) {
            final Claims _claims = claims.get();
            return claimsResolver.apply(_claims);
        }
        return null;
    }

    private Optional<Claims> extractAllClaims(String token) {
        return Optional.ofNullable(Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token.substring(7)) // "Bearer " removed
                .getBody());
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // using UserDetail Object
    public String generateToken(UserDetails userDetails) {
        final Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    // using CustomUser Object
    public String generateToken(CustomUser user, List<String> roles) {
        final Map<String, Object> claims = new HashMap<>(){{ put("claim_role", roles); }};
        return createToken(claims, user.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        final long tokenValidTime = 1000L * 60 * 60 * this.expireHour;
        final SecretKey secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(this.SECRET_KEY));
        final Date issueDate = new Date(System.currentTimeMillis());
        final Date expireDate = new Date(System.currentTimeMillis() + tokenValidTime);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(issueDate)
                .setExpiration(expireDate)
                .signWith(secretKey, SignatureAlgorithm.HS256).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
