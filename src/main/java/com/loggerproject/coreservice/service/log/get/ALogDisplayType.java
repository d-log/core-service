package com.loggerproject.coreservice.service.log.get;

import com.loggerproject.coreservice.data.model.log.content.LogContent;
import com.loggerproject.coreservice.data.model.log.organization.LogOrganization;
import com.loggerproject.coreservice.data.model.shared.GlobalModel;
import lombok.Data;

import java.util.List;

@Data
public abstract class ALogDisplayType extends GlobalModel {
    List<LogContent> logContents;
    LogOrganization logOrganization;

    LogDisplayType logDisplayType;
}
