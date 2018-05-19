package com.loggerproject.coreservice.endpoint.api.log.model;

import com.loggerproject.coreservice.data.model.log.content.LogContent;
import lombok.Data;

import java.util.List;

@Data
public class UpdateLogContentsRequest {
    String id;
    List<LogContent> logContents;
}
