package com.loggerproject.coreservice.server.service.data.log.get.type.page;

import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.data.document.tag.TagModel;
import com.loggerproject.coreservice.server.service.data.log.get.LogType;
import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeModel;
import lombok.Data;

import java.util.List;

@Data
public class LogPageModel extends ALogTypeModel {
    List<DirectoryModel> directoryModels;
    List<TagModel> tagModels;

    public LogPageModel() {
        this.setLogType(LogType.PAGE);
    }
}
