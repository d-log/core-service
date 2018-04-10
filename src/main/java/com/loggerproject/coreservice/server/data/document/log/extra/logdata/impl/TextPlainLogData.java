package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import lombok.Data;

@Data
public class TextPlainLogData {

    // required
    String text;

    // optional
    String font;
    String fontSize;
}
