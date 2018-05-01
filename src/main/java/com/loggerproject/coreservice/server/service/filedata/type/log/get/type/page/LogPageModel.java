package com.loggerproject.coreservice.server.service.filedata.type.log.get.type.page;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogType;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.ATypeFileData;
import lombok.Data;

import java.util.List;

@Data
public class LogPageModel extends ATypeFileData {
    List<FileModel> parentLogDirectoryFileDatas;
    List<FileModel> tagFileDatas;

    public LogPageModel() {
        this.setLogType(LogType.PAGE);
    }
}
