package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl.pojo.image.ImageMetaData;
import lombok.Data;

@Data
public class ImageExternalLogData {
    String imageURL;
    ImageMetaData imageMetaData;
}
