package com.loggerproject.coreservice.data.document.log.model.logdata.image.internal;

import com.loggerproject.coreservice.data.document.log.model.logdata.image.AImageLogData;
import lombok.Data;

@Data
public class InternalImageLogData extends AImageLogData {
    String imageURL;
    String imageID;
}
