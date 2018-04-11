package com.loggerproject.coreservice.server.service.data.log.get.type;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import lombok.Data;

import java.util.Set;

@Data
public abstract class ALogTypeModel extends GlobalModel {
    LogType logType;

    String ID;

    String title;
    String description;

    Set<String> directoryIDs;
    Set<String> tagIDs;
}
