package com.loggerproject.coreservice.server.service.filedata.type.log.get.type.tile;

import com.loggerproject.coreservice.server.service.filedata.type.log.get.ALogFileDataOverride;
import lombok.Data;


@Data
public class TileLogFileDataOverride extends ALogFileDataOverride {
    Integer logDataToDisplayIndex;
}
