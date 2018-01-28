package com.loggerproject.directoryservice.server;

import com.loggerproject.microserviceglobalresource.annotation.mongo.MongoDBConfig;
import com.loggerproject.tagservice.client.EnableTagServiceClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @SpringBootApplication -
 * @EnableEurekaClient -
 * @MongoDBConfig -
 */
@SpringBootApplication
@EnableEurekaClient
@MongoDBConfig
@EnableTagServiceClient
public class DirectoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DirectoryServiceApplication.class, args);
	}
}
