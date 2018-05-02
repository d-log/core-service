package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type.textplain;

import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TextPlainLogDataScrubberValidator extends ALogDataScrubberValidator<TextPlainLogData> {
    @Override
    public TextPlainLogData scrubAndValidateLogData(TextPlainLogData data) {
        Assert.hasText(data.getText(), "TextPlainLogData.text cannot be empty");
        return data;
    }
}
