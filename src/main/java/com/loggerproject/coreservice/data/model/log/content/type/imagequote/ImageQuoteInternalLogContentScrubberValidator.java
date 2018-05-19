package com.loggerproject.coreservice.data.model.log.content.type.imagequote;

import com.loggerproject.coreservice.data.model.log.content.ALogContentScrubberValidator;
import com.loggerproject.coreservice.data.model.log.content.type.image.ImageInternalLogContentScrubberValidator;
import com.loggerproject.coreservice.data.model.log.content.type.textquote.TextQuoteLogContentScrubberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ImageQuoteInternalLogContentScrubberValidator extends ALogContentScrubberValidator<ImageQuoteInternalLogContent> {

    @Autowired
    ImageInternalLogContentScrubberValidator imageInternalLogDataScrubberValidator;

    @Autowired
    TextQuoteLogContentScrubberValidator textQuoteLogDataScrubberValidator;

    @Override
    public ImageQuoteInternalLogContent scrubAndValidateLogData(ImageQuoteInternalLogContent data) throws Exception {
        Assert.notNull(data.getImageContainsQuote(), "ImageQuoteInternalLogContent.imageContainsQuote should be value true or false");
        data.setImageInternalLogData(imageInternalLogDataScrubberValidator.scrubAndValidateLogData(data.getImageInternalLogData()));
        data.setTextQuoteLogData(textQuoteLogDataScrubberValidator.scrubAndValidateLogData(data.getTextQuoteLogData()));
        return data;
    }
}
