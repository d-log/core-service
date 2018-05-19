package com.loggerproject.coreservice.service.log.get.regular.extra;

import com.loggerproject.coreservice.service.log.get.LogDisplayType;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class LogGetterRequest {
    // tells how LogModels should be modified before sending it out
    LogDisplayType logType;

    String searchString;
    String metadataNameRegex;
    Long millisecondThreshold;
    Pageable pageable;
    String parentLogID;
    String tagID;
}
