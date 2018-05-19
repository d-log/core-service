package com.loggerproject.coreservice.service.log.create;

import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.data.model.log.content.LogContentScrubberValidatorService;
import com.loggerproject.coreservice.data.model.log.organization.LogOrganization;
import com.loggerproject.coreservice.data.model.log.override.LogDisplayOverride;
import com.loggerproject.coreservice.data.model.shared.Metadata;
import com.loggerproject.coreservice.data.model.tag.TagModel;
import com.loggerproject.coreservice.data.repository.LogModelRepository;
import com.loggerproject.coreservice.service.aglobal.create.AGlobalModelCreateService;
import com.loggerproject.coreservice.service.log.RootLogModelService;
import com.loggerproject.coreservice.service.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.service.log.get.regular.LogModelGetService;
import com.loggerproject.coreservice.service.log.update.LogModelUpdateService;
import com.loggerproject.coreservice.service.tag.get.TagModelGetService;
import com.loggerproject.coreservice.service.tag.update.TagModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@Service
public class LogModelCreateService extends AGlobalModelCreateService<LogModel> {

    @Autowired
    TagModelGetService tagFileDataGetService;

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    TagModelUpdateService tagModelUpdateService;

    @Autowired
    LogContentScrubberValidatorService logContentScrubberValidatorService;

    @Autowired
    LogModelGetService logModelGetService;

    @Autowired
    LogModelUpdateService logModelUpdateService;

    @Autowired
    RootLogModelService rootLogModelService;

    @Autowired
    public LogModelCreateService(LogModelRepository repository,
                                 @Lazy LogModelCreateService globalServerCreateService,
                                 @Lazy LogModelDeleteService globalServerDeleteService,
                                 @Lazy LogModelGetService globalServerGetService,
                                 @Lazy LogModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public LogModel beforeSaveScrubAndValidate(LogModel model) throws Exception {
        model.setMetadata(beforeScrubAndValidateMetadata(model.getMetadata()));
        model.setLogOrganization(beforeScrubAndValidateLogOrganization(model.getLogOrganization()));
        model.setLogDisplayOverride(beforeScrubAndValidateLogDisplayOverride(model));

        Assert.notEmpty(model.getLogContents(), "LogModel.logContents cannot be empty");
        logContentScrubberValidatorService.scrubAndValidate(model.getLogContents());

        rootLogModelService.validateNotRoot(model);

        return model;
    }

    private Metadata beforeScrubAndValidateMetadata(Metadata metadata) {
        Assert.notNull(metadata, "LogModel.metadata cannot be empty");
        Assert.hasText(metadata.getName(), "LogModel.metadata.name cannot be empty");
        metadata.setDescription(metadata.getDescription() == null ? "" : metadata.getDescription());
        return metadata;
    }

    private LogDisplayOverride beforeScrubAndValidateLogDisplayOverride(LogModel model) throws Exception {
        LogDisplayOverride logDisplayOverride = model.getLogDisplayOverride();

        if (logDisplayOverride != null) {
            if (logDisplayOverride.getTile() != null) {
                Integer logDataToDisplayIndex = logDisplayOverride.getTile().getLogContentToDisplayIndex();
                if (logDataToDisplayIndex != null) {
                    Integer largestIndex = model.getLogContents().size() - 1;
                    if (logDataToDisplayIndex < 0 || logDataToDisplayIndex > largestIndex) {
                        throw new Exception("LogModel.logDisplayOverride.tile.logContentToDisplayIndex has to be a value between 0 and " + largestIndex + " inclusive");
                    }
                }
            }
        }

        return logDisplayOverride;
    }

    private LogOrganization beforeScrubAndValidateLogOrganization(LogOrganization organization) throws Exception {
        Assert.notNull(organization, "LogModel.logOrganization cannot be null");

        Assert.isTrue(CollectionUtils.isEmpty(organization.getChildLogIDs()), "LogModel.logOrganization.childLogIDs should be empty");
        organization.setChildLogIDs(new HashSet<>());

        Assert.notEmpty(organization.getParentLogIDs(), "LogModel.logOrganization.parentLogIDs cannot be empty (you can specify '\" + RootLogModelService.ROOT_NAME + \"' as parent id)\"");
        if (organization.getParentLogIDs().remove(RootLogModelService.ROOT_NAME)) {
            LogModel root = rootLogModelService.getRoot();
            organization.getParentLogIDs().add(root.getId());
        }
        logModelGetService.validateIds(organization.getParentLogIDs());

        organization.setTagIDs(organization.getTagIDs() != null ? organization.getTagIDs() : new HashSet());
        tagFileDataGetService.validateIds(organization.getTagIDs());

        return organization;
    }

    @Override
    protected LogModel afterCreateUpdateOtherDocuments(LogModel model) throws Exception {
        LogOrganization logOrganization = model.getLogOrganization();

        Set<String> parentIDs = logOrganization.getParentLogIDs();
        for (String parentID : parentIDs) {
            LogModel parentLogModel = logModelGetService.findOne(parentID);
            parentLogModel.getLogOrganization().getChildLogIDs().add(model.getId());
            logModelUpdateService.update(parentLogModel);
        }

        Set<String> tagIDs = logOrganization.getTagIDs();
        for (String tagID : tagIDs) {
            TagModel tagModel = tagModelGetService.findOne(tagID);
            tagModel.getLogIDs().add(model.getId());
            tagModelUpdateService.update(tagModel);
        }

        return model;
    }
}
