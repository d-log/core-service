package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import lombok.Data;

@Data
public class ImageQuoteInternalLogData {
    Boolean imageContainsQuote;
    ImageInternalLogData imageInternalLogData;
    TextQuoteLogData textQuoteLogData;
}
