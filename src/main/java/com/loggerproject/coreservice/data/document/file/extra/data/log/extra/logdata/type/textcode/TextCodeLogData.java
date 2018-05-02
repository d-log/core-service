package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type.textcode;

import lombok.Data;

@Data
public class TextCodeLogData {

    // required
    String code;

    // optional
    String language; // such as: java, html, c, etc
    Boolean showLineNumber; // default true
    Integer startingLineNumber; // default 1
    Integer maxHeight; // default -1 for infinite
}