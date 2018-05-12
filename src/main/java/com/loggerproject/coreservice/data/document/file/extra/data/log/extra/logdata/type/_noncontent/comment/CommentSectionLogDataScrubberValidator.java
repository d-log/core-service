package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type._noncontent.comment;

import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;

@Service
public class CommentSectionLogDataScrubberValidator extends ALogDataScrubberValidator<CommentSectionLogData> {

    @Override
    public CommentSectionLogData scrubAndValidateLogData(CommentSectionLogData data) throws Exception {
        return data;
    }
}
