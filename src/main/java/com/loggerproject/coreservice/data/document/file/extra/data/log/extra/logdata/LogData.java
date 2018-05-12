package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata;

import lombok.Data;

import java.util.Map;

@Data
public class LogData {
    // required
    String logDataType;

    Map<String, String> css;
    Object data;
}
