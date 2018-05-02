package com.loggerproject.coreservice.data.document.file.extra.data.log;

import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.LogData;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logtypeoverride.LogTypeOverride;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.organization.Organization;
import com.loggerproject.coreservice.service.file.type.impl.log.get.ALogFileData;
import com.loggerproject.coreservice.service.file.type.impl.log.get.LogType;
import lombok.Data;

import java.util.List;

@Data
public class LogFileData extends ALogFileData {
    List<LogData> logDatas;
    Organization organization;
    LogTypeOverride logTypeOverride;

    public LogFileData() {
        this.setLogType(LogType.DEFAULT);
    }
}
