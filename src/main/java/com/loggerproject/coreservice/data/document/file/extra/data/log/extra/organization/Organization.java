package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.organization;

import lombok.Data;

import java.util.Set;

@Data
public class Organization {
    // Required
    Set<String> parentLogDirectoryFileIDs; // size must be >= 1

    // Optional
    Set<String> tagFileIDs; // if null, instantiate empty collection
}
