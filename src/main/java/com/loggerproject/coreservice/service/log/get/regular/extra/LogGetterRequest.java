package com.loggerproject.coreservice.service.log.get.regular.extra;

import com.loggerproject.coreservice.service.log.get.LogDisplayType;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class LogGetterRequest {
    // tells how LogModels should be modified before sending it out
    LogDisplayType logDisplayType;

    String searchString;

    String metadataNameLike;
    Long createdBefore;
    Pageable pageable;
    String parentLogID;
    String tagID;
}
