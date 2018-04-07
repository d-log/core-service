package com.loggerproject.coreservice.endpoint.api.log.model;

import com.loggerproject.coreservice.data.document.log.model.logdata.ALogData;
import lombok.Data;

import java.util.List;

@Data
public class UpdateLogDatasRequest {
    String id;
    List<ALogData> ALogData;
}
