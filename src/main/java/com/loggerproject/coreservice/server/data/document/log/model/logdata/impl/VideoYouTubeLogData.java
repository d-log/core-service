package com.loggerproject.coreservice.server.data.document.log.model.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.model.logdata.ALogData;
import lombok.Data;

@Data
public class VideoYouTubeLogData extends ALogData {
    String videoID;
}
