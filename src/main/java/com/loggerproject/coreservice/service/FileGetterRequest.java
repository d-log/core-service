package com.loggerproject.coreservice.service;

import com.loggerproject.coreservice.service.log.get.LogDisplayType;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class FileGetterRequest {
    // tells how LogModels should be modified before sending it out
    LogDisplayType logType;

    String searchString;
    String metadataNameRegex;
    Long millisecondThreshold;
    Pageable pageable;
    String parentLogID;
    String tagID;
}
