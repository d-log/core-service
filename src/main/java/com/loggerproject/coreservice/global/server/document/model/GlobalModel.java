package com.loggerproject.coreservice.global.server.document.model;

import com.loggerproject.coreservice.global.server.document.model.MetaData;
import lombok.Data;

@Data
public abstract class GlobalModel {
    MetaData metadata;
    public abstract void setID(String id);
    public abstract String getID();
}
