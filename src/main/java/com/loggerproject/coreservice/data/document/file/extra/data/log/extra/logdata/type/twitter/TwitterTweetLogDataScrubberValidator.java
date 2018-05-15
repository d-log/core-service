package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type.twitter;

import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TwitterTweetLogDataScrubberValidator extends ALogDataScrubberValidator<TwitterTweetLogData> {
    @Override
    public TwitterTweetLogData scrubAndValidateLogData(TwitterTweetLogData data) {
        Assert.hasText(data.getUrl(), "TwitterTweetLogData.url cannot be empty");
        return data;
    }
}
