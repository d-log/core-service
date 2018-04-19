package com.loggerproject.coreservice.server.data.document.log.extra;

import lombok.Data;

import java.util.Set;

@Data
public class LogOrganization {
    // Required
    Set<String> directoryIDs; // size must be >= 1

    // Optional
    Set<String> tagIDs; // if null, instantiate empty collection
}
