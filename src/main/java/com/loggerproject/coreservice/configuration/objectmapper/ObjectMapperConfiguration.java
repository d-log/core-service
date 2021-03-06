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

        // for classes with no fields (eg HeaderSectionLogData and CommentSectionLogData)
        // :[ however, this does not work for some reason, probably a bug
        // for now we would be adding dummy fields to those classes
//        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    }
}
