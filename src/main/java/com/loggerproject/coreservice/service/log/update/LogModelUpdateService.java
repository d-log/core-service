package com.loggerproject.coreservice.service.log.update;

import com.google.common.collect.Sets;
import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.data.model.log.content.LogContentScrubberValidatorService;
import com.loggerproject.coreservice.data.model.log.organization.LogOrganization;
import com.loggerproject.coreservice.data.model.tag.TagModel;
import com.loggerproject.coreservice.data.repository.LogModelRepository;
import com.loggerproject.coreservice.service.aglobal.update.AGlobalModelUpdateService;
import com.loggerproject.coreservice.service.log.RootLogModelService;
import com.loggerproject.coreservice.service.log.create.LogModelCreateService;
import com.loggerproject.coreservice.service.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.service.log.get.regular.LogModelGetService;
import com.loggerproject.coreservice.service.tag.get.TagModelGetService;
import com.loggerproject.coreservice.service.tag.update.TagModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class LogModelUpdateService extends AGlobalModelUpdateService<LogModel> {

    @Autowired
    LogModelGetService logModelGetService;

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    TagModelUpdateService tagModelUpdateService;

    @Autowired
    LogContentScrubberValidatorService logDataScrubberValidatorService;

    @Autowired
    LogModelCreateService logModelCreateService;

    @Autowired
    RootLogModelService rootLogModelService;

    @Autowired
    public LogModelUpdateService(LogModelRepository repository,
                                 @Lazy LogModelCreateService globalServerCreateService,
                                 @Lazy LogModelDeleteService globalServerDeleteService,
                                 @Lazy LogModelGetService globalServerGetService,
                                 @Lazy LogModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public LogModel updateWholeModelAndSyncOtherDocuments(LogModel newModel) throws Exception {
        LogModel oldModel = logModelGetService.validateAndFindOne(newModel.getId());
        if (!rootLogModelService.isRoot(oldModel)) {
            logModelCreateService.beforeSaveScrubAndValidate(newModel);

            LogOrganization oldLogOrganization = oldModel.getLogOrganization();
            LogOrganization newOrganization = newModel.getLogOrganization();

            String newParentLogID = newOrganization.getParentLogIDs().iterator().next();
            oldModel = assignNewParent(oldModel, newParentLogID);

            Set<String> newTagIDs = newOrganization.getTagIDs();
            Set<String> oldTagIDs = oldLogOrganization.getTagIDs();
            oldModel = bindUnbindTags(oldModel, Sets.difference(newTagIDs, oldTagIDs), Sets.difference(oldTagIDs, newTagIDs));

            oldModel.getMetadata().setName(newModel.getMetadata().getName());
            oldModel.getMetadata().setDescription(newModel.getMetadata().getDescription());

            oldModel.setLogContents(newModel.getLogContents());
            oldModel.setLogDisplayOverride(newModel.getLogDisplayOverride());

            return update(oldModel);
        } else {
            // model in subject of update has restricted fields that cannot be updated
            oldModel.getMetadata().setDescription(newModel.getMetadata().getDescription());
            logDataScrubberValidatorService.scrubAndValidate(newModel.getLogContents());
            oldModel.setLogContents(newModel.getLogContents());

            return update(oldModel);
        }
    }

    public LogModel assignNewParent(String logID, String newParentID) throws Exception {
        LogModel model = logModelGetService.validateAndFindOne(logID);
        return assignNewParent(model, newParentID);
    }

    private LogModel assignNewParent(LogModel model, String newParentID) throws Exception {
        if (newParentID.equals(model.getId())) {
            throw new Exception("model cannot be its own parent");
        }

        String oldParentID = model.getLogOrganization().getParentLogIDs().iterator().next();

        if (!oldParentID.equals(newParentID)) {
            LogModel oldParent = logModelGetService.validateAndFindOne(oldParentID);
            LogModel newParent = logModelGetService.validateAndFindOne(newParentID);

            model.getLogOrganization().getParentLogIDs().remove(oldParentID);
            oldParent.getLogOrganization().getChildLogIDs().remove(model.getId());

            model.getLogOrganization().getParentLogIDs().add(newParentID);
            newParent.getLogOrganization().getChildLogIDs().add(model.getId());

            update(oldParent);
            update(newParent);

            return update(model);
        }

        return model;
    }

    private LogModel bindUnbindTags(LogModel model, Set<String> bindTagIDs, Set<String> unbindTagIDs) throws Exception {
        List<TagModel> bindTagModels = tagModelGetService.validateAndFindByIDs(bindTagIDs);
        List<TagModel> unbindTagModels = tagModelGetService.validateAndFindByIDs(unbindTagIDs);

        for (TagModel tagModel : bindTagModels) {
            model.getLogOrganization().getTagIDs().add(tagModel.getId());
            tagModel.getLogIDs().add(model.getId());
            tagModelUpdateService.update(tagModel);
        }
        for (TagModel tagModel : unbindTagModels) {
            model.getLogOrganization().getTagIDs().remove(tagModel.getId());
            tagModel.getLogIDs().remove(model.getId());
            tagModelUpdateService.update(tagModel);
        }

        return update(model);
    }
}
