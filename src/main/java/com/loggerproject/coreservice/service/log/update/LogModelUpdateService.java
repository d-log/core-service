package com.loggerproject.coreservice.service.log.update;

import com.google.common.collect.Sets;
import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.data.model.log.content.LogContent;
import com.loggerproject.coreservice.data.model.log.content.LogContentScrubberValidatorService;
import com.loggerproject.coreservice.data.model.log.organization.LogOrganization;
import com.loggerproject.coreservice.data.model.tag.TagModel;
import com.loggerproject.coreservice.data.repository.LogModelRepository;
import com.loggerproject.coreservice.service.aglobal.update.AGlobalModelUpdateService;
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
    LogModelUpdateService logModelUpdateService;

    @Autowired
    public LogModelUpdateService(LogModelRepository repository,
                                 @Lazy LogModelCreateService globalServerCreateService,
                                 @Lazy LogModelDeleteService globalServerDeleteService,
                                 @Lazy LogModelGetService globalServerGetService,
                                 @Lazy LogModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public LogModel changeName(String id, String name) throws Exception {
        LogModel model = logModelGetService.validateAndFindOne(id);
        model.getMetadata().setName(name);
        return this.update(model);
    }

    public LogModel assignFromParentToParent(String childID, String oldParentID, String newParentID) throws Exception {
        LogModel model = logModelGetService.validateAndFindOne(childID);
        LogModel oldParent = logModelGetService.validateAndFindOne(oldParentID);
        LogModel newParent = logModelGetService.validateAndFindOne(newParentID);

        model.getLogOrganization().getParentLogIDs().remove(oldParentID);
        oldParent.getLogOrganization().getChildLogIDs().remove(childID);

        model.getLogOrganization().getParentLogIDs().add(newParentID);
        newParent.getLogOrganization().getChildLogIDs().add(childID);

        update(oldParent);
        update(newParent);
        return update(model);
    }

    public LogModel assignAdditionalParent(String childID, String parentID) throws Exception {
        LogModel model = logModelGetService.validateAndFindOne(childID);
        LogModel parent = logModelGetService.validateAndFindOne(parentID);

        model.getLogOrganization().getParentLogIDs().add(parentID);
        parent.getLogOrganization().getChildLogIDs().add(childID);

        update(parent);
        return update(model);
    }

    public LogModel updateWholeModelAndSyncOtherDocuments(LogModel newModel) throws Exception {
        logModelCreateService.beforeSaveScrubAndValidate(newModel);

        LogModel oldModel = logModelGetService.validateAndFindOne(newModel.getId());

        LogOrganization oldLogOrganization = oldModel.getLogOrganization();
        Set<String> oldParentLogIDs = oldLogOrganization.getParentLogIDs();
        Set<String> oldTagIDs = oldLogOrganization.getTagIDs();

        LogOrganization newOrganization = newModel.getLogOrganization();
        Set<String> newParentLogIDs = newOrganization.getParentLogIDs();
        Set<String> newTagIDs = newOrganization.getTagIDs();

        oldModel = bindUnbindParentLogs(oldModel, Sets.difference(newParentLogIDs, oldParentLogIDs), Sets.difference(oldParentLogIDs, newParentLogIDs));
        oldModel = bindUnbindTags(oldModel, Sets.difference(newTagIDs, oldTagIDs), Sets.difference(oldTagIDs, newTagIDs));

        oldModel.getMetadata().setName(newModel.getMetadata().getName());
        oldModel.getMetadata().setDescription(newModel.getMetadata().getDescription());

        return update(oldModel);
    }

    public LogModel bindUnbindParentLogs(String id, Set<String> bindDirectoryIDs, Set<String> unbindDirectoryIDs) throws Exception {
        LogModel lf = logModelGetService.validateAndFindOne(id);
        return bindUnbindParentLogs(lf, bindDirectoryIDs, unbindDirectoryIDs);
    }

    private LogModel bindUnbindParentLogs(LogModel model, Set<String> bindParentLogIDs, Set<String> unbindParentLogIDs) throws Exception {
        List<LogModel> bindParentLogModels = logModelGetService.validateAndFindByIDs(bindParentLogIDs);
        List<LogModel> unbindParentLogModels = logModelGetService.validateAndFindByIDs(unbindParentLogIDs);

        for (LogModel parentLogModel : bindParentLogModels) {
            model.getLogOrganization().getParentLogIDs().add(parentLogModel.getId());
            parentLogModel.getLogOrganization().getChildLogIDs().add(model.getId());
            logModelUpdateService.update(parentLogModel);
        }
        for (LogModel parentLogModel : unbindParentLogModels) {
            model.getLogOrganization().getParentLogIDs().remove(parentLogModel.getId());
            parentLogModel.getLogOrganization().getChildLogIDs().remove(model.getId());
            logModelUpdateService.update(parentLogModel);
        }

        return update(model);
    }

    public LogModel bindUnbindTags(String id, Set<String> bindTagIDs, Set<String> unbindTagIDs) throws Exception {
        LogModel lf = logModelGetService.validateAndFindOne(id);
        return bindUnbindTags(lf, bindTagIDs, unbindTagIDs);
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

    public LogModel updateLogContents(String id, List<LogContent> logDatas) throws Exception {
        LogModel lf = logModelGetService.validateAndFindOne(id);
        return updateLogContents(lf, logDatas);
    }

    private LogModel updateLogContents(LogModel model, List<LogContent> logContents) throws Exception {
        logDataScrubberValidatorService.scrubAndValidate(logContents);
        model.setLogContents(logContents);
        return update(model);
    }
}
