package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl.pojo.text.TextType;
import lombok.Data;

@Data
public class TextLogData {

    // required
    TextType textType;
    String text;

    // optional
    String font;
    String fontSize;
}
