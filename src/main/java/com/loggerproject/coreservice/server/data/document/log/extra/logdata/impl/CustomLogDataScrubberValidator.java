package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;

@Service
public class CustomLogDataScrubberValidator extends ALogDataScrubberValidator<CustomLogData> {

    @Override
    public void scrubAndValidateLogData(CustomLogData data) {

    }
}
