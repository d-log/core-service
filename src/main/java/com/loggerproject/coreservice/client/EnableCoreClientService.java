package com.loggerproject.coreservice.client;

import com.loggerproject.coreservice.client.configuration.CoreClientServiceConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(CoreClientServiceConfiguration.class)
public @interface EnableCoreClientService {
}
