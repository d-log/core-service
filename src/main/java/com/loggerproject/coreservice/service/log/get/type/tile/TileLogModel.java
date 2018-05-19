package com.loggerproject.coreservice.service.log.get.type.tile;

import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.data.model.tag.TagModel;
import com.loggerproject.coreservice.service.log.get.ALogDisplayType;
import com.loggerproject.coreservice.service.log.get.LogDisplayType;
import lombok.Data;

import java.util.List;

@Data
public class TileLogModel extends ALogDisplayType {
    String id;
    List<LogModel> parentLogModels;
    List<LogModel> childLogModels;
    List<TagModel> tagModels;
    Integer numLogDatas;

    public TileLogModel() {
        this.setLogDisplayType(LogDisplayType.TILE);
    }
}
