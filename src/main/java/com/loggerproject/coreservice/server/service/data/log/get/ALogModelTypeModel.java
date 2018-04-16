package com.loggerproject.coreservice.server.service.data.log.get;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import lombok.Data;

/**
 * ALogTypeModel and LogModel extends this class
 */
@Data
public abstract class ALogModelTypeModel extends GlobalModel {
    LogType logType;
}
