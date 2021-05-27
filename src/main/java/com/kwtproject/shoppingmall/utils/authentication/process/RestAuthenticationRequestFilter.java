package com.kwtproject.shoppingmall.utils.authentication.process;

import com.kwtproject.shoppingmall.service.UserService;
import com.kwtproject.shoppingmall.utils.authentication.JwtUtils;
import com.kwtproject.shoppingmall.utils.authentication.exception.JwtTokenMissingException;
import com.kwtproject.shoppingmall.utils.authentication.object.AuthenticationObject;
import com.kwtproject.shoppingmall.utils.authentication.object.JwtTokenObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// 1 request 에 대해 한번만 수행
@Component
@Order(0)
public class RestAuthenticationRequestFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith("/user/login")) {
            final String header = request.getHeader("Authorization");

            if (header == null || !header.startsWith("Bearer")) {
                throw new JwtTokenMissingException("ds");
            }

            String tokenStr = header.substring(7); // Header: { Authorization: "Bearer a2h7r4h4hs..." }
            String username = jwtUtils.extractUsername(tokenStr);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userService.loadUserByUsername(username);

                if (jwtUtils.validateToken(tokenStr, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }
        // next filter chain
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
