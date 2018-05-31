package com.loggerproject.coreservice.service.log.get.type.forupdate;

import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.service.log.get.LogDisplayType;
import com.loggerproject.coreservice.service.log.get.regular.LogModelGetService;
import com.loggerproject.coreservice.service.log.get.type.ATypeLogModelGetService;
import com.loggerproject.coreservice.service.tag.get.TagModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ForUpdateLogModelGetService extends ATypeLogModelGetService {

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    LogModelGetService logModelGetService;

    @Override
    public LogDisplayType getLogType() {
        return LogDisplayType.FORUPDATE;
    }

    @Override
    public ForUpdateLogModel getByLogModelInternal(LogModel model) {
        ForUpdateLogModel alt = new ForUpdateLogModel();

        alt.setId(model.getId());
        alt.setMetadata(model.getMetadata());
        alt.setLogOrganization(model.getLogOrganization());
        alt.setLogContents(model.getLogContents());
        alt.setLogDisplayOverride(model.getLogDisplayOverride());
        alt.setParentLogModels(logModelGetService.findByIds(model.getLogOrganization().getParentLogIDs()));
        alt.setChildLogModels(logModelGetService.findByIds(model.getLogOrganization().getChildLogIDs()));
        alt.setTagModels(tagModelGetService.findByIds(model.getLogOrganization().getTagIDs()));

        try {
            alt.setAncestryLogModels(logModelGetService.getAncestryLogModels(model.getId()));
        } catch (Exception e) {
            // TODO log this some where
        }

        return alt;
    }
}
