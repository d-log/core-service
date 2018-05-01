package com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logtypeoverride;

import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.page.PageLogFileDataOverride;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.popup.PopupLogFileDataOverride;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.tile.TileLogFileDataOverride;
import lombok.Data;

@Data
public class LogTypeOverride {
    // nullable in database
    TileLogFileDataOverride tile;
    PageLogFileDataOverride page;
    PopupLogFileDataOverride popup;
}