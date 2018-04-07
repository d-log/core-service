package com.loggerproject.coreservice.server.data.document.log.model.logdata;

import lombok.Data;
import org.springframework.core.GenericTypeResolver;

@Data
public abstract class ALogDataScrubberValidator<T> {
    Class genericClass;

    public ALogDataScrubberValidator() {
        this.genericClass = GenericTypeResolver.resolveTypeArgument(getClass(), ALogDataScrubberValidator.class);
    }

    public abstract void scrubAndValidateLogData(T t) throws Exception;
}
