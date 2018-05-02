package com.loggerproject.coreservice.service.file.type.impl.log.get.regular.extra;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class LogFileGetterRequest {
    String searchString;
    Long millisecondThreshold;
    Pageable pageable;
    String directoryID;
    String tagID;
}
