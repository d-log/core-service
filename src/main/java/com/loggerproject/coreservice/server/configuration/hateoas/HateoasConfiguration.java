package com.loggerproject.coreservice.server.configuration.hateoas;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.RelProvider;

@Configuration
public class HateoasConfiguration {

    @Bean
    public RelProvider relProvider() {
        return new RootRelProvider();
    }
}
