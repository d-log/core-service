package com.loggerproject.coreservice.server.service.data.log.get.type.detail;

import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.data.document.tag.TagModel;
import com.loggerproject.coreservice.server.service.data.log.get.LogType;
import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeModel;
import lombok.Data;

import java.util.List;

@Data
public class LogDetailModel extends ALogTypeModel {
    List<DirectoryModel> directoryModels;
    List<TagModel> tagModels;

    public LogDetailModel() {
        this.setLogType(LogType.DETAIL);
    }
}
