package com.loggerproject.coreservice.data.model.log.content.type.textcode;

import com.loggerproject.coreservice.data.model.log.content.ALogContentScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TextCodeLogContentScrubberValidator extends ALogContentScrubberValidator<TextCodeLogContent> {

    @Override
    public TextCodeLogContent scrubAndValidateLogData(TextCodeLogContent data) {
        Assert.hasText(data.getText(), "TextCodeLogContent.text cannot be empty");
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
