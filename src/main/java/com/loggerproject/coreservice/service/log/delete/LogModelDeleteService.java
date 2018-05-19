package com.loggerproject.coreservice.service.log.delete;

import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.data.model.tag.TagModel;
import com.loggerproject.coreservice.data.repository.LogModelRepository;
import com.loggerproject.coreservice.service.aglobal.delete.AGlobalModelDeleteService;
import com.loggerproject.coreservice.service.aglobal.delete.model.ModelBoundedToLogException;
import com.loggerproject.coreservice.service.log.create.LogModelCreateService;
import com.loggerproject.coreservice.service.log.get.regular.LogModelGetService;
import com.loggerproject.coreservice.service.log.update.LogModelUpdateService;
import com.loggerproject.coreservice.service.tag.get.TagModelGetService;
import com.loggerproject.coreservice.service.tag.update.TagModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class LogModelDeleteService extends AGlobalModelDeleteService<LogModel> {

    @Autowired
    LogModelGetService logModelGetService;

    @Autowired
    LogModelUpdateService logModelUpdateService;

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    TagModelUpdateService tagModelUpdateService;

    @Autowired
    public LogModelDeleteService(LogModelRepository repository,
                                 @Lazy LogModelCreateService globalServerCreateService,
                                 @Lazy LogModelDeleteService globalServerDeleteService,
                                 @Lazy LogModelGetService globalServerGetService,
                                 @Lazy LogModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public LogModel beforeDeleteValidate(LogModel model) throws Exception {
        if (model.getLogOrganization().getChildLogIDs().size() > 0) {
            throw new ModelBoundedToLogException(model.getId(), model.getLogOrganization().getChildLogIDs());
        }
        return model;
    }

    /**
     * TODO error recovery
     * @param model
     * @return
     * @throws Exception
     */
    @Override
    public LogModel afterDeleteUpdateOtherDocuments(LogModel model) throws Exception {
        Set<String> parentIDs = model.getLogOrganization().getParentLogIDs();
        for (String parentID : parentIDs) {
            LogModel parentLogModel = logModelGetService.findOne(parentID);
            if (parentLogModel != null) {
                parentLogModel.getLogOrganization().getChildLogIDs().remove(model.getId());
                logModelUpdateService.update(parentLogModel);
            }
        }

        Set<String> tagIDs = model.getLogOrganization().getTagIDs();
        for (String tagID: tagIDs) {
            TagModel tagModel = tagModelGetService.findOne(tagID);
            tagModel.getLogIDs().remove(model.getId());
            tagModelUpdateService.update(tagModel);
        }

        return model;
    }
}
