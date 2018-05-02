package com.loggerproject.coreservice.server.service.file.extra;

import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogType;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class FileGetterRequest {
    String fileType;

    LogType logType;
    String searchString;
    Long millisecondThreshold;
    Pageable pageable;
    String directoryID;
    String tagID;
}
