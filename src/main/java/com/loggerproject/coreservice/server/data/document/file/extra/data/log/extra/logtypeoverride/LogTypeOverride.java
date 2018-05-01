package com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logtypeoverride;

import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.page.LogPageModel;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.popup.LogPopupModel;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.tile.LogTileModel;
import lombok.Data;

@Data
public class LogTypeOverride {
    // nullable in database
    LogTileModel tile;
    LogPageModel page;
    LogPopupModel popup;
}