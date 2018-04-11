package com.loggerproject.coreservice.server.service.data.log.get.type.tile;

import com.loggerproject.coreservice.server.data.document.log.extra.logdata.LogData;
import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeModel;
import com.loggerproject.coreservice.server.service.data.log.get.type.LogType;
import lombok.Data;
import org.springframework.data.annotation.Transient;

@Data
public class LogTileModel extends ALogTypeModel {
    Integer logDataToDisplayIndex;

    @Transient
    LogData logDataToDisplay;

    public LogTileModel() {
        this.setLogType(LogType.TILE);
    }
}
