package com.loggerproject.coreservice.server.service.data.log.get.type.popup;

import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeModel;
import com.loggerproject.coreservice.server.service.data.log.get.LogType;
import lombok.Data;

@Data
public class LogPopupModel extends ALogTypeModel {

    public LogPopupModel() {
        this.setLogType(LogType.POPUP);
    }
}
