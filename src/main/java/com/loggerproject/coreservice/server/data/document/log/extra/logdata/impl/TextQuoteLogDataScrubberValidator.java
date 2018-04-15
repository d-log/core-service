package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TextQuoteLogDataScrubberValidator extends ALogDataScrubberValidator<TextQuoteLogData> {

    @Override
    public TextQuoteLogData scrubAndValidateLogData(TextQuoteLogData data) {
        Assert.hasText(data.getQuote(), "TextQuoteLogData.quote cannot be empty");

        if (data.getQuoteSource() == null) {
            data.setQuoteSource("unknown");
        }
        if (data.getSourceName() == null) {
            data.setSourceName("unknown");
        }
        if (data.getFormOfCommunication() == null) {
            data.setFormOfCommunication("unknown");
        }

        return data;
    }
}
