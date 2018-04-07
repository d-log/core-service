package com.loggerproject.coreservice.server.endpoint.api.log.model;

import lombok.Data;

import java.util.List;

@Data
public class UpdateBindUnbindRequest {
    String logID;
    List<String> unbindModelIDs;
    List<String> bindModelIDs;
}
