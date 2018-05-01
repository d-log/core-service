package com.loggerproject.coreservice.server.service.filedata.type.log.create;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.logdata.LogDataScrubberValidatorService;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.organization.Organization;
import com.loggerproject.coreservice.server.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.server.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.create.AFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.log.delete.LogFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.log.update.LogFileDataUpdateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.update.LogDirectoryFileDataUpdateService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.get.TagFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.update.TagFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;

@Service
public class LogFileDataCreateService extends AFileDataCreateService<LogFileData> {

    @Autowired
    LogDirectoryFileDataGetService logDirectoryFileDataGetService;

    @Autowired
    TagFileDataGetService tagFileDataGetService;

    @Autowired
    LogDirectoryFileDataUpdateService logDirectoryFileDataUpdateService;

    @Autowired
    TagFileDataUpdateService tagFileDataUpdateService;

    @Autowired
    LogDataScrubberValidatorService logDataScrubberValidatorService;

    @Autowired
    public LogFileDataCreateService(@Lazy LogFileDataCreateService globalServerCreateService,
                                    @Lazy LogFileDataDeleteService globalServerDeleteService,
                                    @Lazy LogFileDataGetService globalServerGetService,
                                    @Lazy LogFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeSaveScrubAndValidate(FileModel model) throws Exception {
        Assert.hasText(model.getMetadata().getName(), "FileModel.metadata.name cannot be empty");

        LogFileData logFileData = (LogFileData) model.getData();
        logFileData.setOrganization(beforeScrubAndValidate(logFileData.getOrganization()));

        Assert.notEmpty(logFileData.getLogDatas(), "FileModel.data.logDatas cannot be empty");
        logDataScrubberValidatorService.scrubAndValidate(logFileData.getLogDatas());

        return model;
    }

    private Organization beforeScrubAndValidate(Organization organization) throws Exception {
        Assert.notNull(organization, "FileModel.data.organization cannot be null");

        Assert.notEmpty(organization.getParentLogDirectoryFileIDs(), "FileModel.data.parentLogDirectoryFileIDs cannot be empty");
        logDirectoryFileDataGetService.validateIds(organization.getParentLogDirectoryFileIDs());

        organization.setTagFileIDs(organization.getTagFileIDs() != null ? organization.getTagFileIDs() : new HashSet());
        tagFileDataGetService.validateIds(organization.getTagFileIDs());

        return organization;
    }

    @Override
    protected FileModel afterCreateUpdateOtherDocuments(FileModel model) throws Exception {
        LogFileData logFileData = (LogFileData) model.getData();

        Organization logOrganization = logFileData.getOrganization();

        for (String id : logOrganization.getParentLogDirectoryFileIDs()) {
            FileModel df = logDirectoryFileDataGetService.validateAndFindOne(id);
            LogDirectoryFileData d = (LogDirectoryFileData) df.getData();
            d.getLogFileIDs().add(model.getId());
            logDirectoryFileDataUpdateService.update(df);
        }

        for (String id : logOrganization.getTagFileIDs()) {
            FileModel tf = tagFileDataGetService.validateAndFindOne(id);
            TagFileData t = (TagFileData) tf.getData();
            t.getLogFileIDs().add(model.getId());
            tagFileDataUpdateService.update(tf);
        }

        return model;
    }
}
