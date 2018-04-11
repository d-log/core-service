package com.loggerproject.coreservice.server.data.document.log.extra.logdata;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class LogData {
    // required
    String logDataType;

    @JsonRawValue
    String data;

    // this allows to serialize actual json which is within this LogData json
    // without it the LogData.data must be in string format with escaped " quotations
    @JsonSetter("data")
    void setDataString(JsonNode data) {
        this.data = data.toString();
    }
}
