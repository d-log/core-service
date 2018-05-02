package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type.imagequote;

import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.ALogDataScrubberValidator;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type.image.ImageInternalLogDataScrubberValidator;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type.textquote.TextQuoteLogDataScrubberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ImageQuoteInternalLogDataScrubberValidator extends ALogDataScrubberValidator<ImageQuoteInternalLogData> {

    @Autowired
    ImageInternalLogDataScrubberValidator imageInternalLogDataScrubberValidator;

    @Autowired
    TextQuoteLogDataScrubberValidator textQuoteLogDataScrubberValidator;

    @Override
    public ImageQuoteInternalLogData scrubAndValidateLogData(ImageQuoteInternalLogData data) throws Exception {
        Assert.notNull(data.getImageContainsQuote(), "ImageQuoteInternalLogData.imageContainsQuote should be value true or false");
        data.setImageInternalLogData(imageInternalLogDataScrubberValidator.scrubAndValidateLogData(data.getImageInternalLogData()));
        data.setTextQuoteLogData(textQuoteLogDataScrubberValidator.scrubAndValidateLogData(data.getTextQuoteLogData()));
        return data;
    }
}
