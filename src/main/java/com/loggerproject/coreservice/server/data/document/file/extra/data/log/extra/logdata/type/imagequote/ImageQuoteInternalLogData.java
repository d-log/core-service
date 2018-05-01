package com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.type.imagequote;


import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.type.image.ImageInternalLogData;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.type.textquote.TextQuoteLogData;
import lombok.Data;

@Data
public class ImageQuoteInternalLogData {
    Boolean imageContainsQuote;
    ImageInternalLogData imageInternalLogData;
    TextQuoteLogData textQuoteLogData;
}
