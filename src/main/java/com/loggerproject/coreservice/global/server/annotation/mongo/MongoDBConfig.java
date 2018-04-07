package com.loggerproject.coreservice.global.server.annotation.mongo;

import com.loggerproject.coreservice.global.server.annotation.mongo.MongoDBConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MongoDBConfiguration.class)
public @interface MongoDBConfig {
}
