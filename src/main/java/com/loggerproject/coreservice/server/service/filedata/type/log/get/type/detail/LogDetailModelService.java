package com.loggerproject.coreservice.server.service.filedata.type.log.get.type.detail;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.ALogTypeGetService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.get.TagFileDataGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogDetailModelService extends ALogTypeGetService<LogDetailModel> {

    @Autowired
    TagFileDataGetService tagFileDataGetService;

    @Autowired
    LogDirectoryFileDataGetService logDirectoryFileDataGetService;

    @Override
    public LogDetailModel getByLogModelInternal(FileModel lf) {
        LogDetailModel logDetail = new LogDetailModel();

        logDetail.setID(lf.getId());
        logDetail.setMetadata(lf.getMetadata());

        LogFileData l = (LogFileData) lf.getData();
        logDetail.setLogDatas(l.getLogDatas());
        logDetail.setOrganization(l.getOrganization());

        logDetail.setParentLogDirectoryFileDatas(logDirectoryFileDataGetService.findByIds(l.getOrganization().getParentLogDirectoryFileIDs()));
        logDetail.setTagFileDatas(tagFileDataGetService.findByIds(l.getOrganization().getTagFileIDs()));

        return logDetail;
    }
}
