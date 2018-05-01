package com.loggerproject.coreservice.server.endpoint.api.filedata.type.log.model;

import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.LogData;
import lombok.Data;

import java.util.List;

@Data
public class UpdateLogDatasRequest {
    String id;
    List<LogData> logDatas;
}
