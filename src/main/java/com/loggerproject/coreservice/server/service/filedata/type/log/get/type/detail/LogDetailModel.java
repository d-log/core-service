package com.loggerproject.coreservice.server.service.filedata.type.log.get.type.detail;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogType;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.ATypeFileData;
import lombok.Data;

import java.util.List;

@Data
public class LogDetailModel extends ATypeFileData {
    List<FileModel> parentLogDirectoryFileDatas;
    List<FileModel> tagFileDatas;

    public LogDetailModel() {
        this.setLogType(LogType.DETAIL);
    }
}
