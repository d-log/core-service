package com.loggerproject.coreservice.server;

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
                                // router
                                "http://192.168.1.2:4200",
                                "http://192.168.1.2:8080",
                                // PUBLIC ADDRESSES - maybe private
                                // global - default ports
                                "http://ui.marcuschiu.com:8080",
                                "http://ui.marcuschiu.com:4200",
                                "http://ui.marcuschiu.com:80",
                                "http://ui.marcuschiu.com");
            }
        };
    }
}
