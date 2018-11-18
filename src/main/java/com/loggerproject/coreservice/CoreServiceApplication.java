package com.loggerproject.coreservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @SpringBootApplication -
 * @EnableSpringDataWebSupport - auto configures Pageable
 */
@SpringBootApplication
@EnableSpringDataWebSupport
public class CoreServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreServiceApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry
                        .addMapping("/**")
                        .allowedMethods("GET", "POST", "PUT")
                        .allowedOrigins(
                                // PRIVATE ADDRESSES
                                "http://localhost:4200",
                                "http://192.168.86.218",
                                "http://192.168.86.217",
                                // PUBLIC ADDRESSES
                                "http://marcuschiu.com");
            }
        };
    }
}
