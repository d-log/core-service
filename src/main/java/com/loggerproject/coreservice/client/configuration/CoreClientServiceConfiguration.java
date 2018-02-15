package com.loggerproject.coreservice.client.configuration;

import com.loggerproject.microserviceglobalresource.client.configuration.ribbon.GlobalRibbonConfiguration;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.loggerproject.coreservice.client.service"})
@RibbonClient(name="core-service", configuration = GlobalRibbonConfiguration.class)
public class CoreClientServiceConfiguration {
}
