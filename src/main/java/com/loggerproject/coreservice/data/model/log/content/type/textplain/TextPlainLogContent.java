package com.loggerproject.coreservice.data.model.log.content.type.textplain;

import lombok.Data;

@Data
public class TextPlainLogContent {

    // required
    String text;

    // optional
    String font;
    String fontSize;
}
