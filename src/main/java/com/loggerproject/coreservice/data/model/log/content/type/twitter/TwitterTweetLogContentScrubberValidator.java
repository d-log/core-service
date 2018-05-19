package com.loggerproject.coreservice.data.model.log.content.type.twitter;

import com.loggerproject.coreservice.data.model.log.content.ALogContentScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class TwitterTweetLogContentScrubberValidator extends ALogContentScrubberValidator<TwitterTweetLogContent> {
    @Override
    public TwitterTweetLogContent scrubAndValidateLogData(TwitterTweetLogContent data) {
        Assert.hasText(data.getUrl(), "TwitterTweetLogContent.url cannot be empty");
        return data;
    }
}
