package com.loggerproject.coreservice.server.service.filedata.type.log.get.type.page;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogType;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.ALogTypeModel;
import lombok.Data;

import java.util.List;

@Data
public class LogPageModel extends ALogTypeModel {
    List<FileModel> parentLogDirectoryFileDatas;
    List<FileModel> tagFileDatas;

    public LogPageModel() {
        this.setLogType(LogType.PAGE);
    }
}
