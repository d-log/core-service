package com.loggerproject.coreservice.server.service.data.log.get.search.model;

import lombok.Data;

@Data
public class SearchRequest {

    // required
    String keyword;

    // nullable
    String logType;
}
