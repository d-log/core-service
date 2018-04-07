package com.loggerproject.coreservice.server;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "logDataClass")
@JsonSubTypes({
        @Type(value = ImageInternalLogData.class, name = "ImageInternalLogData"),
})
public abstract class ALogData {
}
