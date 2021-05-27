package com.kwtproject.shoppingmall.config;

import com.kwtproject.shoppingmall.service.UserService;
import com.kwtproject.shoppingmall.utils.authentication.process.RestAuthenticationRequestFilter;
import com.kwtproject.shoppingmall.utils.authentication.process.RestAuthenticationEntryFilter;
import com.kwtproject.shoppingmall.utils.authentication.process.RestAuthenticationEntryPoint;

import com.kwtproject.shoppingmall.utils.authentication.process.RestAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private RestAuthenticationRequestFilter restAuthenticationRequestFilter;

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception
    {
        http
                .cors()
                .and()
                    .csrf().disable() /** csrf 사용 비활성화 - REST API 방식에 어긋남 */
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .authorizeRequests() /** HttpServletRequest 사용하는 요청들의 접근 제한을 건다. */
                    .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                    .antMatchers("/user/**").permitAll()
                    .antMatchers("/ping").permitAll()
                    .anyRequest().authenticated() /** 나머지 요청 모두 활성화 */
//                .and()
//                    .exceptionHandling().authenticationEntryPoint(new RestAuthenticationEntryPoint()) /** 비인가 접근 진 */
                .and()
                    .formLogin().disable(); /** 기본 spring 로그인 창 비활성화 */

        http.addFilterBefore(restAuthenticationRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    // 필터로 인해 permitAll 이 작동하지 않음
//    @Bean
//    public RestAuthenticationEntryFilter restAuthenticationEntryFilter() throws Exception {
//        RestAuthenticationEntryFilter restAuthenticationEntryFilter = new RestAuthenticationEntryFilter(
//                new AntPathRequestMatcher("/user/**", HttpMethod.POST.name())
//        );
//        restAuthenticationEntryFilter.setAuthenticationManager(this.authenticationManager());
//        restAuthenticationEntryFilter.setAuthenticationSuccessHandler(new RestAuthenticationSuccessHandler());
//        return restAuthenticationEntryFilter;
//    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
