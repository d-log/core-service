package com.loggerproject.coreservice.data.model.log.content.type._section.descendant;

import com.loggerproject.coreservice.data.model.log.content.ALogContentScrubberValidator;
import org.springframework.stereotype.Service;

@Service
public class DescendantLogsSectionLogContentScrubberValidator extends ALogContentScrubberValidator<DescendantLogsSectionLogContent> {

    @Override
    public DescendantLogsSectionLogContent scrubAndValidateLogData(DescendantLogsSectionLogContent data) {
        return data;
    }
}
