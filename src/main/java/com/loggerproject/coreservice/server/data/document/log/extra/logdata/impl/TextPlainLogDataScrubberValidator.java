package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogDataScrubberValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TextPlainLogDataScrubberValidator extends ALogDataScrubberValidator<TextPlainLogData> {
    @Override
    public TextPlainLogData scrubAndValidateLogData(TextPlainLogData data) {
        Assert.isTrue(StringUtils.isNotEmpty(data.getText()), "TextPlainLogData.text cannot be empty");
        return data;
    }
}
