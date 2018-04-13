package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class VideoYouTubeLogDataScrubberValidator extends ALogDataScrubberValidator<VideoYouTubeLogData> {
    @Override
    public VideoYouTubeLogData scrubAndValidateLogData(VideoYouTubeLogData data) {
        Assert.hasText(data.getVideoID(), "VideoYouTubeLogData data.videoID cannot be empty");
        return data;
    }
}
