package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.type.image;

import com.loggerproject.coreservice.data.document.file.extra.data.image.ImageFileData;
import lombok.Data;

@Data
public class ImageInternalLogData {
    String imageID;
    ImageFileData imageFileData;
}
