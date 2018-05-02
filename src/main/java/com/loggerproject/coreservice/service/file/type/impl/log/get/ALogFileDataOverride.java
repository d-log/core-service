package com.loggerproject.coreservice.service.file.type.impl.log.get;

import com.loggerproject.coreservice.data.document.file.extra.metadata.Metadata;
import lombok.Data;

@Data
public abstract class ALogFileDataOverride {
    String id;
    Metadata metadata;
}
