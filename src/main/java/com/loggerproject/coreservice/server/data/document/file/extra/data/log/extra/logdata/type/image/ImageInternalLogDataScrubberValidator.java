package com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.type.image;

import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;

@Service
public class ImageInternalLogDataScrubberValidator extends ALogDataScrubberValidator<ImageInternalLogData> {

//    @Autowired
//    ImageModelGetService imageModelGetService;

    @Override
    public ImageInternalLogData scrubAndValidateLogData(ImageInternalLogData data) throws Exception {
//        ImageModel image = imageModelGetService.validateAndFindOne(filedata.getImageID());
//
//        ImageMetaData imageMetaData = new ImageMetaData();
//        imageMetaData.setHeight(image.getHeight());
//        imageMetaData.setWidth(image.getWidth());
//        imageMetaData.setHeightDivideWidth(image.getHeightDividedByWidth());
//
//        filedata.setImageMetaData(imageMetaData);
//        filedata.setImageURL(image.getImageURL());

        return data;
    }
}
