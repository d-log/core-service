package com.loggerproject.coreservice.server.service.data.log.get.type.page;

import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeGetService;
import com.loggerproject.coreservice.server.service.data.tag.get.TagModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogPageGetService extends ALogTypeGetService<LogPageModel> {

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    DirectoryModelGetService directoryModelGetService;

    @Override
    public LogPageModel getByLogModelInternal(LogModel log) {
        LogPageModel logPage = getBaseLogPageModel(log);
        setLogPageModel(logPage, log);
        return logPage;
    }

    private LogPageModel getBaseLogPageModel(LogModel log) {
        LogPageModel logPage;

        try {
            logPage = log.getLogTypes().getLogPageModel();
        } catch (NullPointerException e) {
            logPage = new LogPageModel();
        }

        return logPage;
    }

    /**
     * TODO set value only when null, so far its replacing everything
     *
     * @param logPage
     * @param log
     */
    private void setLogPageModel(LogPageModel logPage, LogModel log) {
        logPage.setID(log.getID());
        logPage.setMetadata(log.getMetadata());

        logPage.setTitle(log.getTitle());
        logPage.setDescription(log.getDescription());

        logPage.setLogDatas(log.getLogDatas());

        logPage.setLogOrganization(log.getLogOrganization());

        logPage.setDirectoryModels(directoryModelGetService.findByIds(log.getLogOrganization().getDirectoryIDs()));
        logPage.setTagModels(tagModelGetService.findByIds(log.getLogOrganization().getTagIDs()));
    }
}
