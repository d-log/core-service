package com.loggerproject.coreservice.server.data.document.file.extra.data.log;

import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.LogData;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logtypeoverride.LogTypeOverride;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.organization.Organization;
import lombok.Data;

import java.util.List;

@Data
public class LogFileData {
    List<LogData> logDatas;
    Organization organization;
    LogTypeOverride logTypeOverride;
}
