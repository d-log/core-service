package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogDataScrubberValidator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class VideoYouTubeLogDataScrubberValidator extends ALogDataScrubberValidator<VideoYouTubeLogData> {
    @Override
    public VideoYouTubeLogData scrubAndValidateLogData(VideoYouTubeLogData data) {
        Assert.isTrue(StringUtils.isNotEmpty(data.getVideoID()), "VideoYouTubeLogData data.videoID cannot be empty");
        return data;
    }
}
