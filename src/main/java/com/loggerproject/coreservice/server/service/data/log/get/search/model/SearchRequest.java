package com.loggerproject.coreservice.server.service.data.log.get.search.model;

import com.loggerproject.coreservice.server.service.data.log.get.type.LogType;
import lombok.Data;

@Data
public class SearchRequest {

    // required
    String keyword;

    // nullable
    LogType logType;
}
