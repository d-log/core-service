package com.loggerproject.coreservice.server.data.document.log.model.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.model.logdata.ALogData;
import com.loggerproject.coreservice.server.data.document.log.model.logdata.impl.pojo.image.ImageMetaData;
import lombok.Data;

@Data
public class ImageExternalLogData extends ALogData {
    String imageURL;
    ImageMetaData imageMetaData;
}
