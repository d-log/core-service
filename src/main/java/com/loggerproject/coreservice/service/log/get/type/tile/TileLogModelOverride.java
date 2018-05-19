package com.loggerproject.coreservice.service.log.get.type.tile;

import com.loggerproject.coreservice.service.log.get.ALogDisplayTypeOverride;
import lombok.Data;


@Data
public class TileLogModelOverride extends ALogDisplayTypeOverride {
    Integer logContentToDisplayIndex;
}
