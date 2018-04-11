package com.loggerproject.coreservice.server.data.document.log.extra;

import com.loggerproject.coreservice.server.service.data.log.get.type.page.LogPageModel;
import com.loggerproject.coreservice.server.service.data.log.get.type.popup.LogPopupModel;
import com.loggerproject.coreservice.server.service.data.log.get.type.tile.LogTileModel;
import lombok.Data;

@Data
public class LogTypes {
    // nullable in database
    LogTileModel logTileModel;
    LogPageModel logPageModel;
    LogPopupModel logPopupModel;
}
