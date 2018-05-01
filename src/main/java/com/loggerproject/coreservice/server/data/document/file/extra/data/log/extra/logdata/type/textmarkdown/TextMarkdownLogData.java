package com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.type.textmarkdown;

import lombok.Data;

@Data
public class TextMarkdownLogData {

    // required
    String text;

    // optional
    String font;
    String fontSize;
}
