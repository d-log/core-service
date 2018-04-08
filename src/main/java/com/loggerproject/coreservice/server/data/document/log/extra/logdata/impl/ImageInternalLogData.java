package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogData;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl.pojo.image.ImageMetaData;
import lombok.Data;

@Data
public class ImageInternalLogData extends ALogData {
    String imageURL;
    String imageID;
    ImageMetaData imageMetaData;
}
