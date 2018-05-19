package com.loggerproject.coreservice.service.log.get.type.page;

import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.data.model.tag.TagModel;
import com.loggerproject.coreservice.service.log.get.ALogDisplayType;
import com.loggerproject.coreservice.service.log.get.LogDisplayType;
import lombok.Data;

import java.util.List;

@Data
public class PageLogModel extends ALogDisplayType {
    String id;
    List<LogModel> parentLogModels;
    List<LogModel> childLogModels;
    List<TagModel> tagModels;

    public PageLogModel() {
        this.setLogDisplayType(LogDisplayType.PAGE);
    }
}
