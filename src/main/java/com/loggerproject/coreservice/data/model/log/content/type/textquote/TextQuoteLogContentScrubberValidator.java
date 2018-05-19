package com.loggerproject.coreservice.data.model.log.content.type.textquote;

import com.loggerproject.coreservice.data.model.log.content.ALogContentScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

@Service
public class TextQuoteLogContentScrubberValidator extends ALogContentScrubberValidator<TextQuoteLogContent> {

    @Override
    public TextQuoteLogContent scrubAndValidateLogData(TextQuoteLogContent data) {
        Assert.hasText(data.getText(), "TextQuoteLogContent.text cannot be empty");

        if (!StringUtils.hasText(data.getSourceType())) {
            data.setSourceType("unknown");
        }
        if (!StringUtils.hasText(data.getSourceName())) {
            data.setSourceName("unknown");
        }
        if (!StringUtils.hasText(data.getFormOfCommunication())) {
            data.setFormOfCommunication("unknown");
        }

        return data;
    }
}
