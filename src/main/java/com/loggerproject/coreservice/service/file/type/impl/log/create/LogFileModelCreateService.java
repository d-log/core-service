package com.loggerproject.coreservice.service.file.type.impl.log.create;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.LogDataScrubberValidatorService;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logtypeoverride.LogTypeOverride;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.organization.Organization;
import com.loggerproject.coreservice.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.service.file.type.afiledata.create.AFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.log.delete.LogFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.log.get.regular.LogFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.log.update.LogFileModelUpdateService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.regular.LogDirectoryFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.update.LogDirectoryFileModelUpdateService;
import com.loggerproject.coreservice.service.file.type.impl.tag.get.TagFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.tag.update.TagFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashSet;

@Service
public class LogFileModelCreateService extends AFileModelCreateService<LogFileData> {

    @Autowired
    LogDirectoryFileModelGetService logDirectoryFileDataGetService;

    @Autowired
    TagFileModelGetService tagFileDataGetService;

    @Autowired
    LogDirectoryFileModelUpdateService logDirectoryFileDataUpdateService;

    @Autowired
    TagFileModelUpdateService tagFileDataUpdateService;

    @Autowired
    LogDataScrubberValidatorService logDataScrubberValidatorService;

    @Autowired
    public LogFileModelCreateService(@Lazy LogFileModelCreateService globalServerCreateService,
                                     @Lazy LogFileModelDeleteService globalServerDeleteService,
                                     @Lazy LogFileModelGetService globalServerGetService,
                                     @Lazy LogFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeSaveScrubAndValidate(FileModel model) throws Exception {
        Assert.notNull(model.getMetadata(), "FileModel.metadata cannot be empty");
        Assert.hasText(model.getMetadata().getName(), "FileModel.metadata.name cannot be empty");

        LogFileData logFileData = (LogFileData) model.getData();
        logFileData.setOrganization(beforeScrubAndValidateLogTypeOverride(logFileData.getOrganization()));

        Assert.notEmpty(logFileData.getLogDatas(), "FileModel.data.logDatas cannot be empty");
        logDataScrubberValidatorService.scrubAndValidate(logFileData.getLogDatas());

        logFileData.setLogTypeOverride(beforeScrubAndValidateLogTypeOverride(logFileData));

        return model;
    }

    private LogTypeOverride beforeScrubAndValidateLogTypeOverride(LogFileData logFileData) throws Exception {
        LogTypeOverride logTypeOverride = logFileData.getLogTypeOverride();

        if (logTypeOverride != null) {
            if (logTypeOverride.getTile() != null) {
                Integer logDataToDisplayIndex = logTypeOverride.getTile().getLogDataToDisplayIndex();
                if (logTypeOverride.getTile().getLogDataToDisplayIndex() != null) {
                    Integer largestIndex = logFileData.getLogDatas().size() - 1;
                    if (logDataToDisplayIndex < 0 || logDataToDisplayIndex >= largestIndex) {
                        throw new Exception("FileModel.data.logTypeOverride.tile.logDataToDisplayIndex has to be a value between 0 and " + largestIndex + " inclusive");
                    }
                }
            }
        }

        return logTypeOverride;
    }

    private Organization beforeScrubAndValidateLogTypeOverride(Organization organization) throws Exception {
        Assert.notNull(organization, "FileModel.data.organization cannot be null");

        Assert.notEmpty(organization.getParentLogDirectoryFileIDs(), "FileModel.data.organization.parentLogDirectoryFileIDs cannot be empty");
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
