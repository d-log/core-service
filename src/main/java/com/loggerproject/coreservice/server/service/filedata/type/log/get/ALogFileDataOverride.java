package com.loggerproject.coreservice.server.service.filedata.type.log.get;

import com.loggerproject.coreservice.server.data.document.file.extra.metadata.Metadata;
import lombok.Data;

@Data
public abstract class ALogFileDataOverride {
    String id;
    Metadata metadata;
}
