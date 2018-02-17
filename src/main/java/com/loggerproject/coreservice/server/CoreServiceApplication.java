package com.loggerproject.coreservice.server;

import com.loggerproject.directoryservice.client.EnableDirectoryClientService;
import com.loggerproject.imageservice.client.EnableImageClientService;
import com.loggerproject.logservice.client.EnableLogClientService;
import com.loggerproject.microserviceglobalresource.server.annotation.mongo.MongoDBConfig;
import com.loggerproject.tagservice.client.EnableTagClientService;
import com.loggerproject.viewservice.client.EnableViewClientService;
import com.loggerproject.viewservice.client.EnableViewTemplateThemeClientService;
import com.loggerproject.viewtemplateservice.client.EnableViewTemplateClientService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @SpringBootApplication -
 * @EnableEurekaClient -
 */
@SpringBootApplication
@EnableEurekaClient
@EnableLogClientService
@EnableImageClientService
@EnableTagClientService
@EnableDirectoryClientService
@EnableViewTemplateClientService
@EnableViewClientService
@EnableViewTemplateThemeClientService
public class CoreServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreServiceApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:4200");
			}
		};
	}
}
