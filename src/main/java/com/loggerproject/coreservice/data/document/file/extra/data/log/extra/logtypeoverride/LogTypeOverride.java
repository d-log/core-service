package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logtypeoverride;

import com.loggerproject.coreservice.service.file.type.impl.log.get.type.page.PageLogFileDataOverride;
import com.loggerproject.coreservice.service.file.type.impl.log.get.type.popup.PopupLogFileDataOverride;
import com.loggerproject.coreservice.service.file.type.impl.log.get.type.tile.TileLogFileDataOverride;
import lombok.Data;

@Data
public class LogTypeOverride {
    // nullable in database
    TileLogFileDataOverride tile;
    PageLogFileDataOverride page;
    PopupLogFileDataOverride popup;
}