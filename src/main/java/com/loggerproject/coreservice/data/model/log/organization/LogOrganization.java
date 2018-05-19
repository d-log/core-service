package com.loggerproject.coreservice.data.model.log.organization;

import lombok.Data;

import java.util.Set;

@Data
public class LogOrganization {
    // Required
    Set<String> parentLogIDs; // size must be >= 1

    // Optional
    Set<String> childLogIDs;
    Set<String> tagIDs; // if null, instantiate empty collection
}
