package com.loggerproject.coreservice.server.data.document.file.extra.data.logdirectory;

import com.loggerproject.coreservice.server.data.document.file.extra.data.log.LogFileData;
import lombok.Data;

import java.util.Set;

@Data
public class LogDirectoryFileData extends LogFileData {
    Set<String> logFileIDs;
    Set<String> childLogDirectoryFileIDs;
}
