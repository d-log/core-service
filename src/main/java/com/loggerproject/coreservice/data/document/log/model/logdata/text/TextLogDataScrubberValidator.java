package com.loggerproject.coreservice.data.document.log.model.logdata.text;

import com.loggerproject.coreservice.data.document.log.model.logdata.LogDataScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TextLogDataScrubberValidator extends LogDataScrubberValidator<TextLogData> {
    @Override
    public void scrubAndValidateLogData(TextLogData data) {
        Assert.notNull(data.getTextType(), "TextLogData.textType cannot be empty");
    }
}
