package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import lombok.Data;

@Data
public class TextQuoteLogData {
    // required
    String quote;

    // optional, if not provided default to unknown
    String formOfCommunication;
    String quoteSource;
    String sourceName;
}
