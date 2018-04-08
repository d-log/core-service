package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;

@Service
public class ImageExternalLogDataScrubberValidator extends ALogDataScrubberValidator<ImageExternalLogData> {
    @Override
    public ImageExternalLogData scrubAndValidateLogData(ImageExternalLogData data) {
        return data;
    }
}
