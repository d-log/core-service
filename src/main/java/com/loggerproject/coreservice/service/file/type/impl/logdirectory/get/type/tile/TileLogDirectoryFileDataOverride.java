package com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.type.tile;

import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.ALogDirectoryFileDataOverride;
import lombok.Data;


@Data
public class TileLogDirectoryFileDataOverride extends ALogDirectoryFileDataOverride {
    Integer logDataToDisplayIndex;
}
