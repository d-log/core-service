package com.loggerproject.coreservice.server.data.document.log.model.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.model.logdata.ALogData;
import com.loggerproject.coreservice.server.data.document.log.model.logdata.impl.pojo.image.ImageMetaData;
import lombok.Data;

@Data
public class ImageInternalLogData extends ALogData {
    String imageURL;
    String imageID;
    ImageMetaData imageMetaData;
}
