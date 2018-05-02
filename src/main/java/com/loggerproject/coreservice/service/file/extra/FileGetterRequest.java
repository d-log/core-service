package com.loggerproject.coreservice.service.file.extra;

import com.loggerproject.coreservice.service.file.type.impl.log.get.LogType;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.LogDirectoryType;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class FileGetterRequest {
    String fileType;

    // this tells how LogFileModels and LogDirectoryFileModels
    // should be modified before sending it out
    LogType logType;
    LogDirectoryType logDirectoryType;

    String searchString;
    Long millisecondThreshold;
    Pageable pageable;
    String directoryID;
    String tagID;
}
