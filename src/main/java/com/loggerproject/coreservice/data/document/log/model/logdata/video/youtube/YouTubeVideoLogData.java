package com.loggerproject.coreservice.data.document.log.model.logdata.video.youtube;

import com.loggerproject.coreservice.data.document.log.model.logdata.video.AVideoLogData;
import lombok.Data;

@Data
public class YouTubeVideoLogData extends AVideoLogData {
    String videoID;
}
