package com.loggerproject.coreservice.service.file.type.afiledata.delete.model;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Data
public class DeleteAllResponse {
    Set<String> deletedIDs;
    Set<String> notDeletedIDs;
    Map<String, String> reasons;

    public DeleteAllResponse() {
        this.deletedIDs = new HashSet<>();
        this.notDeletedIDs = new HashSet<>();
        this.reasons = new HashMap<>();
    }
}
