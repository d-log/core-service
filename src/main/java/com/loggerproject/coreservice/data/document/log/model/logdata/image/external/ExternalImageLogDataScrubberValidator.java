package com.loggerproject.coreservice.data.document.log.model.logdata.image.external;

import com.loggerproject.coreservice.data.document.log.model.logdata.LogDataScrubberValidator;
import org.springframework.stereotype.Service;

@Service
public class ExternalImageLogDataScrubberValidator extends LogDataScrubberValidator<ExternalImageLogData> {
    @Override
    public void scrubAndValidateLogData(ExternalImageLogData data) {

    }
}
