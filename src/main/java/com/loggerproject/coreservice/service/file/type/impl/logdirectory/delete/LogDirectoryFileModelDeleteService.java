package com.loggerproject.coreservice.service.file.type.impl.logdirectory.delete;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.service.file.type.afiledata.delete.AFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.afiledata.delete.model.ModelBoundedToChildDirectoryException;
import com.loggerproject.coreservice.service.file.type.afiledata.delete.model.ModelBoundedToLogException;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.create.LogDirectoryFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.regular.LogDirectoryFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.update.LogDirectoryFileModelUpdateService;
import com.loggerproject.coreservice.service.file.type.impl.tag.get.TagFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.tag.update.TagFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LogDirectoryFileModelDeleteService extends AFileModelDeleteService<LogDirectoryFileData> {

    @Autowired
    LogDirectoryFileModelGetService logDirectoryFileModelGetService;

    @Autowired
    LogDirectoryFileModelUpdateService logDirectoryFileModelUpdateService;

    @Autowired
    TagFileModelGetService tagFileModelGetService;

    @Autowired
    TagFileModelUpdateService tagFileModelUpdateService;

    @Autowired
    public LogDirectoryFileModelDeleteService(@Lazy LogDirectoryFileModelCreateService globalServerCreateService,
                                              @Lazy LogDirectoryFileModelDeleteService globalServerDeleteService,
                                              @Lazy LogDirectoryFileModelGetService globalServerGetService,
                                              @Lazy LogDirectoryFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeDeleteValidate(FileModel model) throws Exception {
        LogDirectoryFileData directory = (LogDirectoryFileData) model.getData();
        if (directory.getLogFileIDs().size() > 0) {
            throw new ModelBoundedToLogException(model.getId(), directory.getLogFileIDs());
        }
        if (directory.getChildLogDirectoryFileIDs().size() > 0) {
            throw new ModelBoundedToChildDirectoryException(model.getId(), directory.getChildLogDirectoryFileIDs());
        }
        return model;
    }

    @Override
    public FileModel afterDeleteUpdateOtherDocuments(FileModel model) throws Exception {
        LogDirectoryFileData directory = (LogDirectoryFileData) model.getData();

        for (String id : directory.getOrganization().getParentLogDirectoryFileIDs()) {
            FileModel fm = logDirectoryFileModelGetService.findOne(id);
            LogDirectoryFileData parentDirectory = (LogDirectoryFileData) fm.getData();
            parentDirectory.getChildLogDirectoryFileIDs().remove(model.getId());
            logDirectoryFileModelUpdateService.update(fm);
        }

        for (String id : directory.getOrganization().getTagFileIDs()) {
            FileModel fm = tagFileModelGetService.findOne(id);
            TagFileData tag = (TagFileData) fm.getData();
            tag.getLogDirectoryIDs().remove(model.getId());
            tagFileModelUpdateService.update(fm);
        }

        return model;
    }
}
