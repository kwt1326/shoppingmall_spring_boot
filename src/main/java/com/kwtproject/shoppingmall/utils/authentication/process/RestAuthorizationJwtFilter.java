package com.kwtproject.shoppingmall.utils.authentication.process;

import com.kwtproject.shoppingmall.utils.authentication.JwtUtils;
import com.kwtproject.shoppingmall.utils.common.CommonUtils;
import com.kwtproject.shoppingmall.utils.common.ConfigurationPropertiesProvider;

import io.jsonwebtoken.ExpiredJwtException;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
public class RestAuthorizationJwtFilter extends BasicAuthenticationFilter {

    private static final Logger logger = LoggerFactory.getLogger(RestAuthorizationJwtFilter.class);

    private CommonUtils utils;

    private JwtUtils jwtUtils;

    public RestAuthorizationJwtFilter(AuthenticationManager authenticationManager, ConfigurationPropertiesProvider config) {
        super(authenticationManager);
        utils = new CommonUtils();
        jwtUtils = new JwtUtils(config);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        Authentication authentication = getAuthentication(request);
        String header = request.getHeader("Authorization");

        if (utils.isEmpty(header) || !header.startsWith("Bearer")) { // header 가 없으면 과정을 스킵한다.
            chain.doFilter(request, response);
            return;
        }

        // header 가 유효하면 (Authorization 헤더 정의된) 스프링 시큐리티 컨텍스트에 인증정보를 등록한다.
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
System.out.println(token);
        if (utils.isNotEmpty(token)) {
            try {
                // parse JWT
                String username = jwtUtils.extractUsername(token);
                List<SimpleGrantedAuthority> authorityList = jwtUtils.extractAuthorities(token);

                if (utils.isNotEmpty(username)) {
                    return new UsernamePasswordAuthenticationToken(username, null, authorityList);
                }
            } catch (ExpiredJwtException exception) {
                log.warn("Request to parse and expired JWT : {} failed: {}", token, exception.getMessage());
            } catch (Exception exception) {
                log.warn("getAuthentication catch Exception : {}", exception.getMessage());
            }
        }
        return null;
    }
}
