package com.loggerproject.directoryservice.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @SpringBootApplication -
 * @EnableEurekaClient -
 * @ResfreshScope - allows this config client to refresh the @Value application configurations
 * without it, POST call to localhost:port/refresh will not update the @Value variable
 */
@SpringBootApplication
@EnableEurekaClient
@RefreshScope
public class DirectoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DirectoryServiceApplication.class, args);
	}
}
