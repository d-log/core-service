package com.loggerproject.coreservice.server.service.filedata.type.logdirectory.create;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.server.data.document.file.extra.metadata.Metadata;
import com.loggerproject.coreservice.server.service.filedata.afiledata.create.AFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.RootLogDirectoryService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.delete.LogDirectoryFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.update.LogDirectoryFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;

@Service
public class LogDirectoryFileDataCreateService extends AFileDataCreateService<LogDirectoryFileData> {

    @Autowired
    LogDirectoryFileDataGetService tagModelGetService;

    @Autowired
    RootLogDirectoryService rootLogDirectoryService;

    @Autowired
    LogDirectoryFileDataUpdateService logDirectoryFileDataUpdateService;

    @Autowired
    public LogDirectoryFileDataCreateService(@Lazy LogDirectoryFileDataCreateService globalServerCreateService,
                                    @Lazy LogDirectoryFileDataDeleteService globalServerDeleteService,
                                    @Lazy LogDirectoryFileDataGetService globalServerGetService,
                                    @Lazy LogDirectoryFileDataUpdateService globalServerUpdateService) {
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

        return model;
    }
}
