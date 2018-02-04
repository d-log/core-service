package com.loggerproject.directoryservice.client.configuration;

import com.loggerproject.microserviceglobalresource.client.configuration.ribbon.GlobalRibbonConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.loggerproject.directoryservice.client.service"})
@RibbonClient(name="directory-service", configuration = GlobalRibbonConfiguration.class)
public class DirectoryServiceClientConfiguration {
}
