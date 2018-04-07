package com.loggerproject.coreservice.server.data.document.log.model.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.model.logdata.ALogData;
import lombok.Data;

/**
 * All input json LogData would be mapped to this CustomLogData if logDataClass
 * could not be mapped to any other *LogData classes
 */
@Data
public class CustomLogData extends ALogData {
    String data; // json format
}
