package com.loggerproject.coreservice.server.service.filedata.type.log.get.type.tile;

import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogType;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.ATypeFileData;
import lombok.Data;

@Data
public class LogTileModel extends ATypeFileData {
    Integer logDataToDisplayIndex;

    public LogTileModel() {
        this.setLogType(LogType.TILE);
    }
}
