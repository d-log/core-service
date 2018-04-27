package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

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
