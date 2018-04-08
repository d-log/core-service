package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.loggerproject.coreservice.server.data.document.image.ImageModel;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogDataScrubberValidator;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl.pojo.image.ImageMetaData;
import com.loggerproject.coreservice.server.service.data.image.get.ImageModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageInternalLogDataScrubberValidator extends ALogDataScrubberValidator<ImageInternalLogData> {

    @Autowired
    ImageModelGetService imageModelGetService;

    @Override
    public ImageInternalLogData scrubAndValidateLogData(ImageInternalLogData data) throws Exception {
        ImageModel image = imageModelGetService.validateAndFindOne(data.getImageID());

        ImageMetaData imageMetaData = new ImageMetaData();
        imageMetaData.setHeight(image.getHeight());
        imageMetaData.setWidth(image.getWidth());
        imageMetaData.setHeightDivideWidth(image.getHeightDividedByWidth());

        data.setImageMetaData(imageMetaData);
        data.setImageURL(image.getImageURL());

        return data;
    }
}
