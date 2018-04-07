package com.loggerproject.coreservice.data.document.log.model.logdata.text;

import com.loggerproject.coreservice.data.document.log.model.logdata.ALogData;
import lombok.Data;

@Data
public abstract class TextLogData extends ALogData {

    // required
    TextType textType;
    String text;

    // optional
    String font;
    String fontSize;
}
