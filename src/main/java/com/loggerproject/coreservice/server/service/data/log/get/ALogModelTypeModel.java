package com.loggerproject.coreservice.server.service.data.log.get;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import com.loggerproject.coreservice.server.service.data.log.get.type.LogType;
import lombok.Data;

/**
 * Mirrors LogModel without the LogTypes object and other future things
 */
@Data
public abstract class ALogModelTypeModel extends GlobalModel {
    LogType logType;
}
