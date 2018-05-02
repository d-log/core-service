package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type.textquote;

import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class TextQuoteLogDataScrubberValidator extends ALogDataScrubberValidator<TextQuoteLogData> {

    @Override
    public TextQuoteLogData scrubAndValidateLogData(TextQuoteLogData data) {
        Assert.hasText(data.getQuote(), "TextQuoteLogData.quote cannot be empty");

        if (!StringUtils.hasText(data.getSourceType())) {
            data.setSourceType("unknown");
        }
        if (!StringUtils.hasText(data.getSourceName())) {
            data.setSourceName("unknown");
        }
        if (!StringUtils.hasText(data.getFormOfCommunication())) {
            data.setFormOfCommunication("unknown");
        }

        return data;
    }
}
