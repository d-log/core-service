package com.loggerproject.coreservice.server.data.document.log.model.logdata;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.loggerproject.coreservice.server.data.document.log.model.logdata.impl.ImageExternalLogData;
import com.loggerproject.coreservice.server.data.document.log.model.logdata.impl.ImageInternalLogData;
import lombok.Data;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "logDataClass")
@JsonSubTypes({
        @Type(value = ImageInternalLogData.class, name = "ImageInternalLogData"),
        @Type(value = ImageExternalLogData.class, name = "ImageExternalLogData"),
})
public abstract class ALogData {
//    String logDataClass;
}
