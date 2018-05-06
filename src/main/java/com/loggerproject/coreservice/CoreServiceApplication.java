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
                        // allow only GET methods for now
                        .allowedMethods("GET")
                        .allowedOrigins(
                                // PRIVATE ADDRESSES
                                // local machine
                                "http://localhost:4200",
                                "http://localhost:8080",
                                // PUBLIC ADDRESSES - maybe private
                                // global - default ports
                                "http://life.marcuschiu.com:8080",
                                "http://life.marcuschiu.com:4200",
                                "http://life.marcuschiu.com:80",
                                "http://life.marcuschiu.com")
                        .allowedMethods("GET", "POST", "PUT", "DELETE")
                        .allowedOrigins("http://192.168.86.250:8888");
            }
        };
    }
}
