package com.kwtproject.shoppingmall.config;

import com.kwtproject.shoppingmall.repository.IUserRepository;
import com.kwtproject.shoppingmall.repository.JdbcTempleteUserRepository;
import com.kwtproject.shoppingmall.repository.JpaUserRepository;
import com.kwtproject.shoppingmall.repository.UserRepository;
import com.kwtproject.shoppingmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
public class SpringConfig implements WebMvcConfigurer {

    private EntityManager em;
    private DataSource ds;

    @Autowired
    public SpringConfig(EntityManager em, DataSource ds) {
        this.em = em;
        this.ds = ds;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5500");
    }

    /* @Repository @Service 가 없을때 직접 @Bean 으로 등록이 가능하다. */
//    @Bean
//    public UserService userService() { return new UserService(userRepository()); }
//
//    @Bean
//    public IUserRepository userRepository() {
//        return new JpaUserRepository(em);
//    }
}
