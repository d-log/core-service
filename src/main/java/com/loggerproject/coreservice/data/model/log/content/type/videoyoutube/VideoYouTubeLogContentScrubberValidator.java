package com.loggerproject.coreservice.data.model.log.content.type.videoyoutube;

import com.loggerproject.coreservice.data.model.log.content.ALogContentScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class VideoYouTubeLogContentScrubberValidator extends ALogContentScrubberValidator<VideoYouTubeLogContent> {
    @Override
    public VideoYouTubeLogContent scrubAndValidateLogData(VideoYouTubeLogContent data) {
        Assert.hasText(data.getVideoID(), "VideoYouTubeLogContent.videoID cannot be empty");
        return data;
    }
}
