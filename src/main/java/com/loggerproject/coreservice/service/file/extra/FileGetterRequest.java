package com.loggerproject.coreservice.service.file.extra;

import com.loggerproject.coreservice.service.file.type.impl.log.get.LogType;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.LogDirectoryType;
import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.Set;

@Data
public class FileGetterRequest {
    // can be of: LogFileData, LogDirectoryFileData, TagFileData, ImageFileData
    Set<String> fileTypes;

    // this tells how LogFileModels and LogDirectoryFileModels
    // should be modified before sending it out
    LogType logType;
    LogDirectoryType logDirectoryType;

    String searchString;
    String metadataNameRegex;
    Long millisecondThreshold;
    Pageable pageable;
    String directoryID;
    String tagID;
}
