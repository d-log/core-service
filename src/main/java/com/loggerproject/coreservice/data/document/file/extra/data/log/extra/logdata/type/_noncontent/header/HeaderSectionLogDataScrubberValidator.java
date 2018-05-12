package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type._noncontent.header;

import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;

@Service
public class HeaderSectionLogDataScrubberValidator extends ALogDataScrubberValidator<HeaderSectionLogData> {

    @Override
    public HeaderSectionLogData scrubAndValidateLogData(HeaderSectionLogData data) throws Exception {
        return data;
    }
}
