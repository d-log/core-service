package com.loggerproject.coreservice.server.service.filedata.type.log.get.type.popup;

import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogType;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.ATypeFileData;
import lombok.Data;

@Data
public class LogPopupModel extends ATypeFileData {

    public LogPopupModel() {
        this.setLogType(LogType.POPUP);
    }
}
