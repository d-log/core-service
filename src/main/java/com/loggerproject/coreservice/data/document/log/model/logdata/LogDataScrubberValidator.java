package com.loggerproject.coreservice.data.document.log.model.logdata;

import lombok.Data;
import org.springframework.core.GenericTypeResolver;

@Data
public abstract class LogDataScrubberValidator<T> {
    Class genericClass;

    public LogDataScrubberValidator() {
        this.genericClass = GenericTypeResolver.resolveTypeArgument(getClass(), LogDataScrubberValidator.class);
    }

    public abstract void scrubAndValidateLogData(T t) throws Exception;
}
