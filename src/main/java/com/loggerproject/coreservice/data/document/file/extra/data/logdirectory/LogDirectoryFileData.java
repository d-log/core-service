package com.loggerproject.coreservice.data.document.file.extra.data.logdirectory;

import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.LogData;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.organization.Organization;
import com.loggerproject.coreservice.data.document.file.extra.data.logdirectory.extra.LogDirectoryTypeOverride;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.ALogDirectoryFileData;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.LogDirectoryType;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class LogDirectoryFileData extends ALogDirectoryFileData {
    // log stuff
    List<LogData> logDatas;
    Organization organization;

    // directory stuff
    Set<String> logFileIDs;
    Set<String> childLogDirectoryFileIDs;
    LogDirectoryTypeOverride logDirectoryTypeOverride;

    public LogDirectoryFileData() {
        this.setLogDirectoryType(LogDirectoryType.DEFAULT);
    }
}
