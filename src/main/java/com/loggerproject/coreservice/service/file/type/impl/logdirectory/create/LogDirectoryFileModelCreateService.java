package com.loggerproject.coreservice.service.file.type.impl.logdirectory.create;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.data.document.file.extra.metadata.Metadata;
import com.loggerproject.coreservice.service.file.type.afiledata.create.AFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.RootLogDirectoryService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.delete.LogDirectoryFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.regular.LogDirectoryFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.update.LogDirectoryFileModelUpdateService;
import com.loggerproject.coreservice.service.file.type.impl.tag.get.TagFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.tag.update.TagFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@Service
public class LogDirectoryFileModelCreateService extends AFileModelCreateService<LogDirectoryFileData> {

    @Autowired
    LogDirectoryFileModelGetService tagModelGetService;

    @Autowired
    RootLogDirectoryService rootLogDirectoryService;

    @Autowired
    LogDirectoryFileModelUpdateService logDirectoryFileDataUpdateService;

    @Autowired
    TagFileModelGetService tagFileModelGetService;

    @Autowired
    TagFileModelUpdateService tagFileModelUpdateService;

    @Autowired
    public LogDirectoryFileModelCreateService(@Lazy LogDirectoryFileModelCreateService globalServerCreateService,
                                              @Lazy LogDirectoryFileModelDeleteService globalServerDeleteService,
                                              @Lazy LogDirectoryFileModelGetService globalServerGetService,
                                              @Lazy LogDirectoryFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeSaveScrubAndValidate(FileModel model) throws Exception {
        LogDirectoryFileData directory = (LogDirectoryFileData) model.getData();

        if (!CollectionUtils.isEmpty(directory.getLogFileIDs())) {
            throw new Exception("FileModel.data.logFileIDs should be empty");
        }
        if (!CollectionUtils.isEmpty(directory.getChildLogDirectoryFileIDs())) {
            throw new Exception("FileModel.data.childLogDirectoryFileIDs should be empty");
        }
        Assert.notNull(directory.getOrganization(), "FileModel.data.organization cannot be empty");
        Assert.notEmpty(directory.getOrganization().getParentLogDirectoryFileIDs(), "FileModel.data.organization.parentLogDirectoryFileIDs cannot be empty (you can specify '" + RootLogDirectoryService.ROOT_NAME + "' as parent id)");
        Assert.isTrue(CollectionUtils.isEmpty(directory.getOrganization().getTagFileIDs()), "Filemodel.data.organization.tagFileIDs must be empty");
        directory.getOrganization().setTagFileIDs(new HashSet<>());

        directory.setLogFileIDs(new HashSet<>());
        directory.setChildLogDirectoryFileIDs(new HashSet<>());
        model.setMetadata(model.getMetadata() == null ? new Metadata() : model.getMetadata());
        model.getMetadata().setName(model.getMetadata().getName() == null ? "" : model.getMetadata().getName());
        model.getMetadata().setDescription(model.getMetadata().getDescription() == null ? "" : model.getMetadata().getDescription());

        rootLogDirectoryService.validateNotRoot(model);

        if (directory.getOrganization().getParentLogDirectoryFileIDs().remove(RootLogDirectoryService.ROOT_NAME)) {
            FileModel root = rootLogDirectoryService.getRoot();
            directory.getOrganization().getParentLogDirectoryFileIDs().add(root.getId());
        }

        globalServerGetService.validateIds(directory.getOrganization().getParentLogDirectoryFileIDs());

        return model;
    }

    @Override
    protected FileModel afterCreateUpdateOtherDocuments(FileModel model) throws Exception {
        LogDirectoryFileData directory = (LogDirectoryFileData) model.getData();

        Set<String> parentIDs = directory.getOrganization().getParentLogDirectoryFileIDs();
        for (String parentID : parentIDs) {
            FileModel df = globalServerGetService.findOne(parentID);
            LogDirectoryFileData d = (LogDirectoryFileData) df.getData();
            d.getChildLogDirectoryFileIDs().add(model.getId());
            logDirectoryFileDataUpdateService.update(df);
        }

        Set<String> tagIDs = directory.getOrganization().getTagFileIDs();
        for (String tagID : tagIDs) {
            FileModel fm = tagFileModelGetService.findOne(tagID);
            TagFileData d = (TagFileData) fm.getData();
            d.getLogDirectoryIDs().add(model.getId());
            tagFileModelUpdateService.update(fm);
        }

        return model;
    }
}
