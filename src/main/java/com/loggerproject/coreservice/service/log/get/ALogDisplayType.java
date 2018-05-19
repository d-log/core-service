package com.loggerproject.coreservice.service.log.get;

import com.loggerproject.coreservice.data.model._shared.Metadata;
import com.loggerproject.coreservice.data.model.log.content.LogContent;
import com.loggerproject.coreservice.data.model.log.organization.LogOrganization;
import lombok.Data;

import java.util.List;

@Data
public abstract class ALogDisplayType {
    Metadata metadata;

    List<LogContent> logContents;
    LogOrganization logOrganization;

    LogDisplayType logDisplayType;

    public abstract String getId();
    public abstract void setId(String id);
}
