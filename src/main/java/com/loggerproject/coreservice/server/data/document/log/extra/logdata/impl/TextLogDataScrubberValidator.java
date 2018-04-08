package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TextLogDataScrubberValidator extends ALogDataScrubberValidator<TextLogData> {
    @Override
    public void scrubAndValidateLogData(TextLogData data) {
        Assert.notNull(data.getTextType(), "TextLogData.textType cannot be empty");
    }
}
