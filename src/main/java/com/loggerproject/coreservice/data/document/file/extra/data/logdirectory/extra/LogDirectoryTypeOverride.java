package com.loggerproject.coreservice.data.document.file.extra.data.logdirectory.extra;

import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.type.page.PageLogDirectoryFileDataOverride;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.type.tile.TileLogDirectoryFileDataOverride;
import lombok.Data;

@Data
public class LogDirectoryTypeOverride {
    PageLogDirectoryFileDataOverride page;
    TileLogDirectoryFileDataOverride tile;
}
