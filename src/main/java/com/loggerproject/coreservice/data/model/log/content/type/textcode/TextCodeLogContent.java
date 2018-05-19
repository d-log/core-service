package com.loggerproject.coreservice.data.model.log.content.type.textcode;

import lombok.Data;

@Data
public class TextCodeLogContent {

    // required
    String text;

    // optional
    String language; // such as: java, html, c, etc
    Boolean showLineNumber; // default true
    Integer startingLineNumber; // default 1
    Integer maxHeight; // default -1 for infinite
}
