package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata;

import lombok.Data;

@Data
public class LogData {
    // required
    String logDataType;

    Object data;
}
