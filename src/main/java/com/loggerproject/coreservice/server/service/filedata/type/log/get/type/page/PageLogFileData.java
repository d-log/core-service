package com.loggerproject.coreservice.server.service.filedata.type.log.get.type.page;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.LogData;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.organization.Organization;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.ALogFileData;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogType;
import lombok.Data;

import java.util.List;

@Data
public class PageLogFileData extends ALogFileData {
    Organization organization;
    List<LogData> logDatas;
    List<FileModel> parentLogDirectoryFileDatas;
    List<FileModel> tagFileDatas;

    public PageLogFileData() {
        this.setLogType(LogType.PAGE);
    }
}
