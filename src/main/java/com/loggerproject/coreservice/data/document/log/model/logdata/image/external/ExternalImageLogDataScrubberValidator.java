package com.loggerproject.coreservice.data.document.log.model.logdata.image.external;

import com.loggerproject.coreservice.data.document.log.model.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;

@Service
public class ExternalImageLogDataScrubberValidator extends ALogDataScrubberValidator<ExternalImageLogData> {
    @Override
    public void scrubAndValidateLogData(ExternalImageLogData data) {

    }
}
