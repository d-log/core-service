package com.loggerproject.coreservice.data.model.shared;

import lombok.Data;

@Data
public abstract class GlobalModel {
    Metadata metadata;

    public abstract void setId(String id);

    public abstract String getId();
}
