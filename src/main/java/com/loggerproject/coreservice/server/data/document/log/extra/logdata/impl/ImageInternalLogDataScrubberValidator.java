package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogDataScrubberValidator;
import com.loggerproject.coreservice.server.service.data.image.get.ImageModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageInternalLogDataScrubberValidator extends ALogDataScrubberValidator<ImageInternalLogData> {

    @Autowired
    ImageModelGetService imageModelGetService;

    @Override
    public void scrubAndValidateLogData(ImageInternalLogData data) throws Exception {
        imageModelGetService.validateId(data.getImageID());
    }
}
