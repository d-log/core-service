package com.loggerproject.coreservice.server.data.document.log.model.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.model.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TextLogDataScrubberValidator extends ALogDataScrubberValidator<TextLogData> {
    @Override
    public void scrubAndValidateLogData(TextLogData data) {
        Assert.notNull(data.getTextType(), "TextLogData.textType cannot be empty");
    }
}
