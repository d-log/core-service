package com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.type.image;

import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.type.image.extra.ImageMetaData;
import lombok.Data;

@Data
public class ImageInternalLogData {
    String imageID;
    String imageURL;
    ImageMetaData imageMetaData;
}
