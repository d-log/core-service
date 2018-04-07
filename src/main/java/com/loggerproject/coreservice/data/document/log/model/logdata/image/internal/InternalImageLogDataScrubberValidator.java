package com.loggerproject.coreservice.data.document.log.model.logdata.image.internal;

import com.loggerproject.coreservice.data.document.log.model.logdata.LogDataScrubberValidator;
import com.loggerproject.coreservice.service.data.image.get.ImageModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternalImageLogDataScrubberValidator extends LogDataScrubberValidator<InternalImageLogData> {

    @Autowired
    ImageModelGetService imageModelGetService;

    @Override
    public void scrubAndValidateLogData(InternalImageLogData data) throws Exception {
        imageModelGetService.validateId(data.getImageID());
    }
}
