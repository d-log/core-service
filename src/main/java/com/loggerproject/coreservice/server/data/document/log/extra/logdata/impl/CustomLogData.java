package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogData;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * All input json LogData would be mapped to this CustomLogData if logDataClass
 * could not be mapped to any other *LogData classes
 */
@Data
public class CustomLogData extends ALogData {

    Map<String, Object> properties = new LinkedHashMap<>();

    @JsonAnySetter
    public void set(String name, Object value) {
        this.properties.put(name, value);
    }

    @JsonAnyGetter
    public Map<String, Object> properties() {
        return this.properties;
    }
}
