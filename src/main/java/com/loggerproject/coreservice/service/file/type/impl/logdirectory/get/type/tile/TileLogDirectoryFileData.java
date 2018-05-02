package com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.type.tile;

import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.LogData;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.organization.Organization;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.ALogDirectoryFileData;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.LogDirectoryType;
import lombok.Data;

import java.util.List;

@Data
public class TileLogDirectoryFileData extends ALogDirectoryFileData {
    Organization organization;
    List<LogData> logDatas;

    public TileLogDirectoryFileData() {
        this.setLogDirectoryType(LogDirectoryType.TILE);
    }
}
