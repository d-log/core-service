package com.loggerproject.coreservice.data.model.log.override;

import com.loggerproject.coreservice.service.log.get.type.page.PageLogModelOverride;
import com.loggerproject.coreservice.service.log.get.type.popup.PopupLogModelOverride;
import com.loggerproject.coreservice.service.log.get.type.tile.TileLogModelOverride;
import lombok.Data;

@Data
public class LogDisplayOverride {
    // nullable in database
    TileLogModelOverride tile;
    PageLogModelOverride page;
    PopupLogModelOverride popup;
}