package com.loggerproject.coreservice.data.document.log.model.logdata.image;

import com.loggerproject.coreservice.data.document.log.model.logdata.ALogData;
import lombok.Data;

@Data
public abstract class AImageLogData extends ALogData {
    ImageMetaData imageMetaData;
}
