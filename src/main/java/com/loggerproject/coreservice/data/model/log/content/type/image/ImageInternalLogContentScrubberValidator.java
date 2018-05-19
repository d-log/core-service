package com.loggerproject.coreservice.data.model.log.content.type.image;

import com.loggerproject.coreservice.data.model.image.ImageModel;
import com.loggerproject.coreservice.data.model.log.content.ALogContentScrubberValidator;
import com.loggerproject.coreservice.service.image.get.ImageModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ImageInternalLogContentScrubberValidator extends ALogContentScrubberValidator<ImageInternalLogContent> {

    @Autowired
    ImageModelGetService imageGetService;

    @Override
    public ImageInternalLogContent scrubAndValidateLogData(ImageInternalLogContent data) throws Exception {
        Assert.notNull(data.getImageModel(), "ImageInternalLogContent.imageModel cannot be empty");
        Assert.notNull(data.getImageModel().getId(), "ImageInternalLogContent.imageModel.id cannot be empty");

        ImageModel model = imageGetService.validateAndFindOne(data.getImageModel().getId());

        data.setImageModel(model);

        return data;
    }
}
