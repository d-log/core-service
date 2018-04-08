package com.loggerproject.coreservice.server.endpoint.api.log.model;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogData;
import lombok.Data;

import java.util.List;

@Data
public class UpdateLogDatasRequest {
    String id;
    List<ALogData> ALogData;
}
