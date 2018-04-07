package com.loggerproject.coreservice.data.document.log.model.logdata.custom;

import com.loggerproject.coreservice.data.document.log.model.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;

@Service
public class CustomLogDataScrubberValidator extends ALogDataScrubberValidator<CustomLogData> {
    @Override
    public void scrubAndValidateLogData(CustomLogData data) {

    }
}
