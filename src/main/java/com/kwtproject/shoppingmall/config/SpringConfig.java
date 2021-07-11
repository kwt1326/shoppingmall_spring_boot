package com.kwtproject.shoppingmall.config;

import com.kwtproject.shoppingmall.repository.user.IUserRepository;
import com.kwtproject.shoppingmall.repository.user.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

@Configuration
public class SpringConfig implements WebMvcConfigurer {

    @PersistenceContext
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
}
