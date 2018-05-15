package com.loggerproject.coreservice.endpoint.api.file.type.log.model;

import lombok.Data;

import java.util.Set;

@Data
public class UpdateBindUnbindRequest {
    String logID;
    Set<String> unbindModelIDs;
    Set<String> bindModelIDs;
}
