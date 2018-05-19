package com.loggerproject.coreservice.data.model.log.content.type.imagequote;


import com.loggerproject.coreservice.data.model.log.content.type.image.ImageInternalLogContent;
import com.loggerproject.coreservice.data.model.log.content.type.textquote.TextQuoteLogContent;
import lombok.Data;

@Data
public class ImageQuoteInternalLogContent {
    Boolean imageContainsQuote;
    ImageInternalLogContent imageInternalLogData;
    TextQuoteLogContent textQuoteLogData;
}
