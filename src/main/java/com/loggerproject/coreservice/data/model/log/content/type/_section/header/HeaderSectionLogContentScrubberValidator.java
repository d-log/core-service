package com.loggerproject.coreservice.data.model.log.content.type._section.header;

import com.loggerproject.coreservice.data.model.log.content.ALogContentScrubberValidator;
import org.springframework.stereotype.Service;

@Service
public class HeaderSectionLogContentScrubberValidator extends ALogContentScrubberValidator<HeaderSectionLogContent> {

    @Override
    public HeaderSectionLogContent scrubAndValidateLogData(HeaderSectionLogContent data) {
        return data;
    }
}
