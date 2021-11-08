package com.kwtproject.shoppingmall.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.Arrays;

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
                .allowedOrigins("http://localhost:3000")
                .exposedHeaders("Authorization", "Content-Disposition");
    }
}
