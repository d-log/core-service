package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;

@Data
public abstract class ALogDataScrubberValidator<T> {
    Class genericClass;

    @Autowired
    ObjectMapper objectMapper;

    public ALogDataScrubberValidator() {
        this.genericClass = GenericTypeResolver.resolveTypeArgument(getClass(), ALogDataScrubberValidator.class);
    }

    public abstract T scrubAndValidateLogData(T t) throws Exception;

    @SuppressWarnings(value = "unchecked")
    public T scrubAndValidateLogDataObject(Object data) throws Exception {
        Object t;
        if (data != null) {
            t = objectMapper.convertValue(data, genericClass);
        } else {
            t = genericClass.newInstance();
        }
        return scrubAndValidateLogData((T) t);
    }
}
