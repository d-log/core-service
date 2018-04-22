package com.loggerproject.coreservice.server.service.data.log.get.type.detail;

import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeGetService;
import com.loggerproject.coreservice.server.service.data.tag.get.TagModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogDetailModelService extends ALogTypeGetService<LogDetailModel> {

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    DirectoryModelGetService directoryModelGetService;

    @Override
    public LogDetailModel getByLogModelInternal(LogModel log) {
        LogDetailModel logDetail = new LogDetailModel();

        logDetail.setID(log.getID());
        logDetail.setMetadata(log.getMetadata());

        logDetail.setTitle(log.getTitle());
        logDetail.setDescription(log.getDescription());

        logDetail.setLogDatas(log.getLogDatas());

        logDetail.setLogOrganization(log.getLogOrganization());

        logDetail.setDirectoryModels(directoryModelGetService.findByIds(log.getLogOrganization().getDirectoryIDs()));
        logDetail.setTagModels(tagModelGetService.findByIds(log.getLogOrganization().getTagIDs()));

        return logDetail;
    }
}
