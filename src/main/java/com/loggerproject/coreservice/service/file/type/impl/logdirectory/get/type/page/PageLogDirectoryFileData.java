package com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.type.page;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.LogData;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.organization.Organization;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.ALogDirectoryFileData;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.LogDirectoryType;
import lombok.Data;

import java.util.List;

@Data
public class PageLogDirectoryFileData extends ALogDirectoryFileData {
    Organization organization;
    List<LogData> logDatas;
    List<FileModel> parentLogDirectoryFileDatas;
    List<FileModel> tagFileDatas;

    public PageLogDirectoryFileData() {
        this.setLogDirectoryType(LogDirectoryType.PAGE);
    }
}
