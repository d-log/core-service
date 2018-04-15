package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogDataScrubberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageQuoteInternalLogDataScrubberValidator extends ALogDataScrubberValidator<ImageQuoteInternalLogData> {

    @Autowired
    ImageInternalLogDataScrubberValidator imageInternalLogDataScrubberValidator;

    @Autowired
    TextQuoteLogDataScrubberValidator textQuoteLogDataScrubberValidator;

    @Override
    public ImageQuoteInternalLogData scrubAndValidateLogData(ImageQuoteInternalLogData data) throws Exception {
        data.setImageInternalLogData(imageInternalLogDataScrubberValidator.scrubAndValidateLogData(data.getImageInternalLogData()));
        data.setTextQuoteLogData(textQuoteLogDataScrubberValidator.scrubAndValidateLogData(data.getTextQuoteLogData()));
        return data;
    }
}
