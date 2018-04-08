package com.loggerproject.coreservice.server.data.document.log.extra.logdata;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl.*;
import lombok.Data;

@Data
@JsonTypeInfo(
        use         = JsonTypeInfo.Id.NAME,
        include     = JsonTypeInfo.As.PROPERTY,
        property    = "logDataType",
        visible     = true,
        defaultImpl = CustomLogData.class)
@JsonSubTypes({
        @Type(value = ImageInternalLogData.class,   name = "ImageInternal"),
        @Type(value = ImageExternalLogData.class,   name = "ImageExternal"),
        @Type(value = VideoYouTubeLogData.class,    name = "YouTubeVideo"),
        @Type(value = TextLogData.class,            name = "Text")
})
public abstract class ALogData {
    String logDataType;
}
