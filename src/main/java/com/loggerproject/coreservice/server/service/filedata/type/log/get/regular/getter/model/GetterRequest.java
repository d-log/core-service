package com.loggerproject.coreservice.server.service.filedata.type.log.get.regular.getter.model;

import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class GetterRequest {
    String searchString;
    Long millisecondThreshold;
    Pageable pageable;
    String directoryID;
    String tagID;
}
