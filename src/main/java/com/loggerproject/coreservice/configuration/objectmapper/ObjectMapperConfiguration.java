package com.loggerproject.coreservice.configuration.objectmapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {

    @Autowired
    public void configureObjectMapper(ObjectMapper objectMapper) {
        // only in jackson-databind 2.9.0, however it is not compatible with Spring 1.5.9.RELEASE
//        objectMapper.enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS);
    }
}
