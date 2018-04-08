package com.loggerproject.coreservice.server.data.document.log.extra.logdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;

@Data
public abstract class ALogDataScrubberValidator<T> {
    Class genericClass;
    String genericClassName;

    @Autowired
    ObjectMapper objectMapper;

    public ALogDataScrubberValidator() {
        this.genericClass = GenericTypeResolver.resolveTypeArgument(getClass(), ALogDataScrubberValidator.class);
        this.genericClassName = genericClass.getSimpleName();
    }

    public abstract T scrubAndValidateLogData(T t) throws Exception;

    @SuppressWarnings(value = "unchecked")
    public String scrubAndValidateLogDataString(String data) throws Exception {
        T t = (T)objectMapper.readValue(data, genericClass);
        t = scrubAndValidateLogData(t);
        return objectMapper.writeValueAsString(t);
    }
}
