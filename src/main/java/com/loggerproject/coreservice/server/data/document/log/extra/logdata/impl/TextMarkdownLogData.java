package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import lombok.Data;

@Data
public class TextMarkdownLogData {

    // required
    String text;

    // optional
    String font;
    String fontSize;
}
