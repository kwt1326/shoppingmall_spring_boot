package com.kwtproject.shoppingmall.utils.authentication.process;

import com.kwtproject.shoppingmall.utils.authentication.exception.JwtTokenMissingException;
import com.kwtproject.shoppingmall.utils.authentication.object.JwtTokenObject;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestAuthenticationEntryFilter extends AbstractAuthenticationProcessingFilter {
    public RestAuthenticationEntryFilter() {
        super("/**");
    }

    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer")) {
            throw new JwtTokenMissingException("ds");
        }

        String tokenStr = header.substring(7); // Header: { Authorization: "Bearer a2h7r4h4hs..." }

        JwtTokenObject jwtTokenAuthentication = new JwtTokenObject(tokenStr);

        return getAuthenticationManager().authenticate(jwtTokenAuthentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        // As this authentication is in HTTP header, after success we need to continue the request normally
        // and return the response as if the resource was not secured at all
        // 즉 기본인증이 filter chain 을 중단하고 redirection 을 수행하므로 응답 생성을 포함하여 완전히 실행하기 위해 재정의가 필요합니다.
        chain.doFilter(request, response);
    }


}
