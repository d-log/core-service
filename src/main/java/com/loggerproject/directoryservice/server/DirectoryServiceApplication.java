package com.loggerproject.directoryservice.server;

import com.loggerproject.microserviceglobalresource.server.annotation.mongo.MongoDBConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @SpringBootApplication -
 * @EnableEurekaClient -
 * @MongoDBConfig -
 */
@SpringBootApplication
@EnableEurekaClient
@MongoDBConfig
public class DirectoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DirectoryServiceApplication.class, args);
	}
}
