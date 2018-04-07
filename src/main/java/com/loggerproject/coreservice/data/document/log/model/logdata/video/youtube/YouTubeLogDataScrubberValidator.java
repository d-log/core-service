package com.loggerproject.coreservice.data.document.log.model.logdata.video.youtube;

import com.loggerproject.coreservice.data.document.log.model.logdata.ALogDataScrubberValidator;
import org.springframework.stereotype.Service;

@Service
public class YouTubeLogDataScrubberValidator extends ALogDataScrubberValidator<YouTubeVideoLogData> {
    @Override
    public void scrubAndValidateLogData(YouTubeVideoLogData data) {

    }
}
