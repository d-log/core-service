package com.loggerproject.coreservice.service.log.get;

import com.loggerproject.coreservice.data.model.shared.Metadata;
import lombok.Data;

@Data
public abstract class ALogDisplayTypeOverride {
    String id;
    Metadata metadata;
}
