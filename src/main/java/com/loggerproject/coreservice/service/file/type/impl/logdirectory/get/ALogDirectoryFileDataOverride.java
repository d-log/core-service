package com.loggerproject.coreservice.service.file.type.impl.logdirectory.get;

import com.loggerproject.coreservice.data.document.file.extra.metadata.Metadata;
import lombok.Data;

@Data
public abstract class ALogDirectoryFileDataOverride {
    String id;
    Metadata metadata;
}
