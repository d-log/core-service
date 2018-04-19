package com.loggerproject.coreservice.server.service.data.log.get.type;

import com.loggerproject.coreservice.server.data.document.log.extra.LogOrganization;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.LogData;
import com.loggerproject.coreservice.server.service.data.log.get.ALogModelTypeModel;
import lombok.Data;

import java.util.List;

/**
 * Mirrors LogModel without the LogTypes object and other future things
 */
@Data
public abstract class ALogTypeModel extends ALogModelTypeModel {
    String ID;

    String title;
    String description;

    LogOrganization logOrganization;

    List<LogData> logDatas;
}
