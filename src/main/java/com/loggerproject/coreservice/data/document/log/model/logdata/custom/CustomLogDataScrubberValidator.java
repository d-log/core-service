package com.loggerproject.coreservice.data.document.log.model.logdata.custom;

import com.loggerproject.coreservice.data.document.log.model.logdata.LogDataScrubberValidator;
import org.springframework.stereotype.Service;

@Service
public class CustomLogDataScrubberValidator extends LogDataScrubberValidator<CustomLogData> {
    @Override
    public void scrubAndValidateLogData(CustomLogData data) {

    }
}
