package com.loggerproject.coreservice.server.data.document.log.model.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.model.logdata.ALogData;
import com.loggerproject.coreservice.server.data.document.log.model.logdata.impl.pojo.text.TextType;
import lombok.Data;

@Data
public class TextLogData extends ALogData {

    // required
    TextType textType;
    String text;

    // optional
    String font;
    String fontSize;
}
