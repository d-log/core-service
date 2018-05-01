package com.loggerproject.coreservice.server.service.filedata.type.log.get.type.tile;

import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.LogData;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.organization.Organization;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.ALogFileData;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogType;
import lombok.Data;

import java.util.List;

@Data
public class TileLogFileData extends ALogFileData {
    Organization organization;
    List<LogData> logDatas;

    public TileLogFileData() {
        this.setLogType(LogType.TILE);
    }
}
