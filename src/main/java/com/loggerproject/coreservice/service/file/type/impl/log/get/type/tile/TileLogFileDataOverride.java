package com.loggerproject.coreservice.service.file.type.impl.log.get.type.tile;

import com.loggerproject.coreservice.service.file.type.impl.log.get.ALogFileDataOverride;
import lombok.Data;


@Data
public class TileLogFileDataOverride extends ALogFileDataOverride {
    Integer logDataToDisplayIndex;
}
