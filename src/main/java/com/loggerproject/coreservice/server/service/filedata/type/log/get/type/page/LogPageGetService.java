package com.loggerproject.coreservice.server.service.filedata.type.log.get.type.page;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.type.ALogTypeGetService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.get.TagFileDataGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogPageGetService extends ALogTypeGetService<LogPageModel> {

    @Autowired
    TagFileDataGetService tagFileDataGetService;

    @Autowired
    LogDirectoryFileDataGetService logDirectoryFileDataGetService;

    @Override
    public LogPageModel getByLogModelInternal(FileModel log) {
        LogPageModel logPage = getBaseLogPageModel(log);
        setLogPageModel(logPage, log);
        return logPage;
    }

    private LogPageModel getBaseLogPageModel(FileModel lf) {
        LogPageModel logPage;

        try {
            LogFileData l = (LogFileData) lf.getData();
            logPage = l.getLogTypeOverride().getPage();
        } catch (NullPointerException e) {
            logPage = new LogPageModel();
        }

        return logPage;
    }

    /**
     * TODO set value only when null, so far its replacing everything
     *
     * @param logPage
     * @param lf
     */
    private void setLogPageModel(LogPageModel logPage, FileModel lf) {
        logPage.setID(lf.getId());
        logPage.setMetadata(lf.getMetadata());

        LogFileData l = (LogFileData) lf.getData();
        logPage.setLogDatas(l.getLogDatas());
        logPage.setOrganization(l.getOrganization());

        logPage.setParentLogDirectoryFileDatas(logDirectoryFileDataGetService.findByIds(l.getOrganization().getParentLogDirectoryFileIDs()));
        logPage.setTagFileDatas(tagFileDataGetService.findByIds(l.getOrganization().getTagFileIDs()));
    }
}
