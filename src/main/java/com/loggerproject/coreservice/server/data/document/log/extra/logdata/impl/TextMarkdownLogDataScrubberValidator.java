package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TextMarkdownLogDataScrubberValidator extends ALogDataScrubberValidator<TextMarkdownLogData> {
    @Override
    public TextMarkdownLogData scrubAndValidateLogData(TextMarkdownLogData data) {
        Assert.hasText(data.getText(), "TextMarkdownLogData.text cannot be empty");
        return data;
    }
}
