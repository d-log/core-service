package com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.type.videoyoutube;

import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class VideoYouTubeLogDataScrubberValidator extends ALogDataScrubberValidator<VideoYouTubeLogData> {
    @Override
    public VideoYouTubeLogData scrubAndValidateLogData(VideoYouTubeLogData data) {
        Assert.hasText(data.getVideoID(), "VideoYouTubeLogData filedata.videoID cannot be empty");
        return data;
    }
}
