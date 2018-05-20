package com.loggerproject.coreservice.data.model.log.content.type._section.childlogs;

import com.loggerproject.coreservice.data.model.log.content.ALogContentScrubberValidator;
import org.springframework.stereotype.Service;

@Service
public class ChildLogsSectionLogContentScrubberValidator extends ALogContentScrubberValidator<ChildLogsSectionLogContent> {

    @Override
    public ChildLogsSectionLogContent scrubAndValidateLogData(ChildLogsSectionLogContent data) {
        return data;
    }
}
