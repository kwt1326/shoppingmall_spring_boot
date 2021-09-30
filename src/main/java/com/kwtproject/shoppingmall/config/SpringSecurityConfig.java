package com.kwtproject.shoppingmall.config;

import com.kwtproject.shoppingmall.security.CustomAccessDeniedHandler;
import com.kwtproject.shoppingmall.service.UserService;
import com.kwtproject.shoppingmall.utils.authentication.process.CustomSuccessHandler;
import com.kwtproject.shoppingmall.utils.authentication.process.RestAuthenticationJwtFilter;
import com.kwtproject.shoppingmall.utils.authentication.process.RestAuthorizationJwtFilter;
import com.kwtproject.shoppingmall.utils.common.ConfigurationPropertiesProvider;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/ui",
                "/swagger-resources", "/configuration/security",
                "/swagger-ui.html", "/webjars/**", "/swagger/**",
                "/resources/**", "/static/**", "/css/**"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .cors()
                .and()
                    .csrf().disable() /** csrf 사용 비활성화 - REST API 방식에 어긋남 */
                    .formLogin().disable() /** 기본 spring 로그인 창 비활성화 */
                    .authorizeRequests() /** HttpServletRequest 사용하는 요청들의 접근 제한을 건다. */
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    // PUBLIC - COMMON
                    .antMatchers("/public/**").permitAll()
                    .antMatchers("/user/auth/**").permitAll()
                    // ADMIN
                    .antMatchers("/login").permitAll()
                    .antMatchers("/product/**").permitAll()
                    // OTHERS
                    .anyRequest().authenticated() /** 나머지 요청 모두 활성화 */
                .and()
                    .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilter(new RestAuthenticationJwtFilter(authenticationManager(), configurationPropertiesProvider()));
        http.addFilter(new RestAuthorizationJwtFilter(authenticationManager(), configurationPropertiesProvider()));
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService())
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public ConfigurationPropertiesProvider configurationPropertiesProvider() { return new ConfigurationPropertiesProvider(); }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() { return new CustomSuccessHandler(); }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() { return new CustomAccessDeniedHandler(); }

    @Bean
    public UserDetailsService userDetailService() {
        return new UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
