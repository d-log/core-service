package com.loggerproject.coreservice.data.model.log.content;

import lombok.Data;

import java.util.Map;

@Data
public class LogContent {
    String logContentType;
    Map<String, String> css;
    Object data;
}
