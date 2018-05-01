package com.loggerproject.coreservice.server.service.filedata.type.log.get.type.tile;

import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogType;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.ALogTypeModel;
import lombok.Data;

@Data
public class LogTileModel extends ALogTypeModel {
    Integer logDataToDisplayIndex;

    /**
     * stores which logData(s) should be displayed on Tile
     */

    public LogTileModel() {
        this.setLogType(LogType.TILE);
    }
}
