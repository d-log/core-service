package com.loggerproject.coreservice.server.data.document.file.extra.data.tag;

import lombok.Data;

import java.util.Set;

@Data
public class TagFileData {
    Set<String> logFileIDs;
    String imageFileID;
}
