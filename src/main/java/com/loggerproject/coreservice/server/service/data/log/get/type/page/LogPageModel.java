package com.loggerproject.coreservice.server.service.data.log.get.type.page;

import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeModel;
import com.loggerproject.coreservice.server.service.data.log.get.type.LogType;
import lombok.Data;

@Data
public class LogPageModel extends ALogTypeModel {
    public LogPageModel() {
        this.setLogType(LogType.PAGE);
    }
}