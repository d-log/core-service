package com.loggerproject.coreservice.data.model.log.content.type._section.comment;

import com.loggerproject.coreservice.data.model.log.content.ALogContentScrubberValidator;
import org.springframework.stereotype.Service;

@Service
public class CommentSectionLogContentScrubberValidator extends ALogContentScrubberValidator<CommentSectionLogContent> {

    @Override
    public CommentSectionLogContent scrubAndValidateLogData(CommentSectionLogContent data) {
        return data;
    }
}
