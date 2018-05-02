package com.loggerproject.coreservice.data.document.file.extra.data.tag;

import lombok.Data;

import java.util.Set;

@Data
public class TagFileData {
    Set<String> logFileIDs;
    Set<String> logDirectoryIDs;
    String imageFileID;
}
