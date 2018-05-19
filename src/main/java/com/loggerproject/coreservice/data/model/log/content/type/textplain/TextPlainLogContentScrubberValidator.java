package com.loggerproject.coreservice.data.model.log.content.type.textplain;

import com.loggerproject.coreservice.data.model.log.content.ALogContentScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TextPlainLogContentScrubberValidator extends ALogContentScrubberValidator<TextPlainLogContent> {
    @Override
    public TextPlainLogContent scrubAndValidateLogData(TextPlainLogContent data) {
        Assert.hasText(data.getText(), "TextPlainLogContent.text cannot be empty");
        return data;
    }
}
