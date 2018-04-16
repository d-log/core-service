package com.loggerproject.coreservice.server.service.data.log.get.type.tile;

import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeModel;
import com.loggerproject.coreservice.server.service.data.log.get.type.LogType;
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
