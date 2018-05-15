package com.loggerproject.coreservice.service.file.type.impl.log.update;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Sets;
import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.LogData;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.LogDataScrubberValidatorService;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.organization.Organization;
import com.loggerproject.coreservice.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.service.file.type.afiledata.update.AFileModelUpdateService;
import com.loggerproject.coreservice.service.file.type.impl.log.create.LogFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.log.delete.LogFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.log.get.regular.LogFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.regular.LogDirectoryFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.update.LogDirectoryFileModelUpdateService;
import com.loggerproject.coreservice.service.file.type.impl.tag.get.TagFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.tag.update.TagFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class LogFileModelUpdateService extends AFileModelUpdateService<LogFileData> {

    @Autowired
    LogFileModelGetService logGetService;

    @Autowired
    LogDirectoryFileModelGetService directoryGetService;

    @Autowired
    LogDirectoryFileModelUpdateService directoryUpdateService;

    @Autowired
    TagFileModelGetService tagGetService;

    @Autowired
    TagFileModelUpdateService tagUpdateService;

    @Autowired
    LogDataScrubberValidatorService logDataScrubberValidatorService;

    @Autowired
    LogFileModelCreateService logFileModelCreateService;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    public LogFileModelUpdateService(@Lazy LogFileModelCreateService globalServerCreateService,
                                     @Lazy LogFileModelDeleteService globalServerDeleteService,
                                     @Lazy LogFileModelGetService globalServerGetService,
                                     @Lazy LogFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public FileModel updateWholeModelAndSyncOtherDocuments(FileModel newModel) throws Exception {
        convertFileDataObject2Generic(newModel);
        logFileModelCreateService.beforeSaveScrubAndValidate(newModel);

        FileModel oldModel = logGetService.validateAndFindOne(newModel.getId());

        LogFileData logFileData = (LogFileData) oldModel.getData();
        Organization oldOrganization = logFileData.getOrganization();
        Set<String> oldDirectoryIDs = oldOrganization.getParentLogDirectoryFileIDs();
        Set<String> oldTagIDs = oldOrganization.getTagFileIDs();

        Organization newOrganization = ((LogFileData) newModel.getData()).getOrganization();
        Set<String> newDirectoryIDs = newOrganization.getParentLogDirectoryFileIDs();
        Set<String> newTagIDs = newOrganization.getTagFileIDs();

        oldModel = bindUnbindDirectories(oldModel, Sets.difference(newDirectoryIDs, oldDirectoryIDs), Sets.difference(oldDirectoryIDs, newDirectoryIDs));
        oldModel = bindUnbindTags(oldModel, Sets.difference(newTagIDs, oldTagIDs), Sets.difference(oldTagIDs, newTagIDs));

        oldModel.getMetadata().setName(newModel.getMetadata().getName());
        oldModel.getMetadata().setDescription(newModel.getMetadata().getDescription());
        oldModel.setData(newModel.getData());

        return update(oldModel);
    }

    public FileModel bindUnbindDirectories(String id, Set<String> bindDirectoryIDs, Set<String> unbindDirectoryIDs) throws Exception {
        FileModel lf = logGetService.validateAndFindOne(id);
        return bindUnbindDirectories(lf, bindDirectoryIDs, unbindDirectoryIDs);
    }

    private FileModel bindUnbindDirectories(FileModel lf, Set<String> bindDirectoryIDs, Set<String> unbindDirectoryIDs) throws Exception {
        LogFileData l = (LogFileData) lf.getData();

        List<FileModel> bindDirectories = new ArrayList<>();
        List<FileModel> unbindDirectories = new ArrayList<>();

        for (String directoryID : bindDirectoryIDs) {
            FileModel df = directoryGetService.validateAndFindOne(directoryID);
            bindDirectories.add(df);
        }
        for (String directoryID : unbindDirectoryIDs) {
            FileModel df = directoryGetService.validateAndFindOne(directoryID);
            unbindDirectories.add(df);
        }

        for (FileModel df : bindDirectories) {
            LogDirectoryFileData d = (LogDirectoryFileData) df.getData();
            l.getOrganization().getParentLogDirectoryFileIDs().add(df.getId());
            d.getLogFileIDs().add(lf.getId());
            directoryUpdateService.update(df);
        }
        for (FileModel df : unbindDirectories) {
            LogDirectoryFileData d = (LogDirectoryFileData) df.getData();
            l.getOrganization().getParentLogDirectoryFileIDs().remove(df.getId());
            d.getLogFileIDs().remove(lf.getId());
            directoryUpdateService.update(df);
        }

        return update(lf);
    }

    public FileModel bindUnbindTags(String id, Set<String> bindTagIDs, Set<String> unbindTagIDs) throws Exception {
        FileModel lf = logGetService.validateAndFindOne(id);
        return bindUnbindTags(lf, bindTagIDs, unbindTagIDs);
    }

    private FileModel bindUnbindTags(FileModel lf, Set<String> bindTagIDs, Set<String> unbindTagIDs) throws Exception {
        LogFileData l = (LogFileData) lf.getData();

        List<FileModel> bindTags = new ArrayList<>();
        List<FileModel> unbindTags = new ArrayList<>();

        for (String tagID : bindTagIDs) {
            FileModel tag = tagGetService.validateAndFindOne(tagID);
            bindTags.add(tag);
        }
        for (String tagID : unbindTagIDs) {
            FileModel tag = tagGetService.validateAndFindOne(tagID);
            unbindTags.add(tag);
        }

        for (FileModel tf : bindTags) {
            TagFileData t = (TagFileData) tf.getData();
            l.getOrganization().getTagFileIDs().add(tf.getId());
            t.getLogFileIDs().add(lf.getId());
            tagUpdateService.update(tf);
        }
        for (FileModel tf : unbindTags) {
            TagFileData t = (TagFileData) tf.getData();
            l.getOrganization().getTagFileIDs().remove(tf.getId());
            t.getLogFileIDs().remove(lf.getId());
            tagUpdateService.update(tf);
        }

        return update(lf);
    }

    public FileModel updateLogDatas(String id, List<LogData> logDatas) throws Exception {
        FileModel lf = logGetService.validateAndFindOne(id);
        return updateLogDatas(lf, logDatas);
    }

    private FileModel updateLogDatas(FileModel lf, List<LogData> logDatas) throws Exception {
        LogFileData l = (LogFileData) lf.getData();
        logDataScrubberValidatorService.scrubAndValidate(logDatas);
        l.setLogDatas(logDatas);
        return update(lf);
    }
}
