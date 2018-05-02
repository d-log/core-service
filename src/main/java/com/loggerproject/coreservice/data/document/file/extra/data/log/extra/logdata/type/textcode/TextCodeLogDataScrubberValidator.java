package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type.textcode;

import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TextCodeLogDataScrubberValidator extends ALogDataScrubberValidator<TextCodeLogData> {

    @Override
    public TextCodeLogData scrubAndValidateLogData(TextCodeLogData data) {
        Assert.hasText(data.getCode(), "TextCodeLogData.code cannot be empty");
        if (data.getStartingLineNumber() == null) {
            data.setStartingLineNumber(1);
        }
        if (data.getShowLineNumber() == null) {
            data.setShowLineNumber(true);
        }
        if (data.getMaxHeight() == null || data.getMaxHeight() < -1) {
            data.setMaxHeight(-1); // meaning infinite
        }
        return data;
    }
}