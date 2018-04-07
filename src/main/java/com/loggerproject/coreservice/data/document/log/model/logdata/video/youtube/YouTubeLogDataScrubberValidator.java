package com.loggerproject.coreservice.data.document.log.model.logdata.video.youtube;

import com.loggerproject.coreservice.data.document.log.model.logdata.LogDataScrubberValidator;
import org.springframework.stereotype.Service;

@Service
public class YouTubeLogDataScrubberValidator extends LogDataScrubberValidator<YouTubeVideoLogData> {
    @Override
    public void scrubAndValidateLogData(YouTubeVideoLogData data) {

    }
}
