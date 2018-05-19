package com.loggerproject.coreservice.data.model.log.content.type.textmarkdown;

import com.loggerproject.coreservice.data.model.log.content.ALogContentScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TextMarkdownLogContentScrubberValidator extends ALogContentScrubberValidator<TextMarkdownLogContent> {
    @Override
    public TextMarkdownLogContent scrubAndValidateLogData(TextMarkdownLogContent data) {
        Assert.hasText(data.getText(), "TextMarkdownLogContent.text cannot be empty");
        return data;
    }
}
