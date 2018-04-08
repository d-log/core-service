package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogData;
import lombok.Data;

@Data
public class VideoYouTubeLogData extends ALogData {
    String videoID;
}
