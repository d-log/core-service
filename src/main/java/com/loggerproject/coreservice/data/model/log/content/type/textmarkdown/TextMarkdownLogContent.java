package com.loggerproject.coreservice.data.model.log.content.type.textmarkdown;

import lombok.Data;

@Data
public class TextMarkdownLogContent {
    // required
    String type;
    String text;
}
