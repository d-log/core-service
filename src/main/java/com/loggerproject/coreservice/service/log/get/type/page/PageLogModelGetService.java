package com.loggerproject.coreservice.service.log.get.type.page;

import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.service.log.get.LogDisplayType;
import com.loggerproject.coreservice.service.log.get.regular.LogModelGetService;
import com.loggerproject.coreservice.service.log.get.type.ATypeLogModelGetService;
import com.loggerproject.coreservice.service.tag.get.TagModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PageLogModelGetService extends ATypeLogModelGetService {

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    LogModelGetService logModelGetService;

    @Override
    public LogDisplayType getLogType() {
        return LogDisplayType.PAGE;
    }


    @Override
    public PageLogModel getByLogModelInternal(LogModel model) {
        PageLogModel alt = new PageLogModel();

        alt.setId(model.getId());
        alt.setMetadata(model.getMetadata());
        alt.setLogOrganization(model.getLogOrganization());
        alt.setLogContents(model.getLogContents());
        alt.setParentLogModels(logModelGetService.findByIds(model.getLogOrganization().getParentLogIDs()));
        alt.setChildLogModels(logModelGetService.findByIds(model.getLogOrganization().getChildLogIDs()));
        alt.setTagModels(tagModelGetService.findByIds(model.getLogOrganization().getTagIDs()));

        return alt;
    }

    private PageLogModelOverride getBaseLogPageModel(LogModel model) {
        PageLogModelOverride override;

        try {
            override = model.getLogDisplayOverride().getPage();
        } catch (NullPointerException e) {
            override = new PageLogModelOverride();
        }

        return override;
    }
}
