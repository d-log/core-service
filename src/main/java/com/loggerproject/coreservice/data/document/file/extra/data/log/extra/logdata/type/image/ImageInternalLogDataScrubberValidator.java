package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type.image;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.image.ImageFileData;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.ALogDataScrubberValidator;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type.image.extra.ImageMetaData;
import com.loggerproject.coreservice.service.file.type.impl.image.get.ImageFileModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageInternalLogDataScrubberValidator extends ALogDataScrubberValidator<ImageInternalLogData> {

    @Autowired
    ImageFileModelGetService imageGetService;

    @Override
    public ImageInternalLogData scrubAndValidateLogData(ImageInternalLogData data) throws Exception {
        FileModel model = imageGetService.validateAndFindOne(data.getImageID());

        ImageFileData image = (ImageFileData) model.getData();
        ImageMetaData imageMetaData = new ImageMetaData();
        imageMetaData.setHeight(image.getHeight());
        imageMetaData.setWidth(image.getWidth());
        imageMetaData.setHeightDivideWidth(image.getHeightDividedByWidth());

        data.setImageMetaData(imageMetaData);
        data.setImageURL(image.getImageURL());

        return data;
    }
}
