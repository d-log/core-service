package com.loggerproject.coreservice.service.log.get.type.forupdate;

import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.data.model.log.override.LogDisplayOverride;
import com.loggerproject.coreservice.data.model.tag.TagModel;
import com.loggerproject.coreservice.service.log.get.ALogDisplayType;
import com.loggerproject.coreservice.service.log.get.LogDisplayType;
import lombok.Data;

import java.util.List;

@Data
public class ForUpdateLogModel extends ALogDisplayType {
    String id;
    LogDisplayOverride logDisplayOverride;
    List<LogModel> parentLogModels;
    List<LogModel> childLogModels;
    List<TagModel> tagModels;
    List<LogModel> ancestryLogModels; // from root log to main direct parent, this is computed upon request

    public ForUpdateLogModel() {
        this.setLogDisplayType(LogDisplayType.FORUPDATE);
    }
}
