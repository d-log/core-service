package com.loggerproject.coreservice.endpoint.api.file.type.log.model;

import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.LogData;
import lombok.Data;

import java.util.List;

@Data
public class UpdateLogDatasRequest {
    String id;
    List<LogData> logDatas;
}
