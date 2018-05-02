package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type.textplain;

import lombok.Data;

@Data
public class TextPlainLogData {

    // required
    String text;

    // optional
    String font;
    String fontSize;
}
