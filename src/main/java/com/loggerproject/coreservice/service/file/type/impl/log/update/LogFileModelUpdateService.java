package com.loggerproject.coreservice.service.file.type.impl.log.update;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.LogData;
import com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata.LogDataScrubberValidatorService;
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
    public LogFileModelUpdateService(@Lazy LogFileModelCreateService globalServerCreateService,
                                     @Lazy LogFileModelDeleteService globalServerDeleteService,
                                     @Lazy LogFileModelGetService globalServerGetService,
                                     @Lazy LogFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public FileModel bindUnbindDirectories(String id, List<String> bindDirectoryIDs, List<String> unbindDirectoryIDs) throws Exception {
        FileModel lf = logGetService.validateAndFindOne(id);
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
            d.getLogFileIDs().add(id);
            directoryUpdateService.update(df);
        }
        for (FileModel df : unbindDirectories) {
            LogDirectoryFileData d = (LogDirectoryFileData) df.getData();
            l.getOrganization().getParentLogDirectoryFileIDs().remove(df.getId());
            d.getLogFileIDs().remove(id);
            directoryUpdateService.update(df);
        }

        return update(lf);
    }

    public FileModel bindUnbindTags(String id, List<String> bindTagIDs, List<String> unbindTagIDs) throws Exception {
        FileModel lf = logGetService.validateAndFindOne(id);
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
            t.getLogFileIDs().add(id);
            tagUpdateService.update(tf);
        }
        for (FileModel tf : unbindTags) {
            TagFileData t = (TagFileData) tf.getData();
            l.getOrganization().getTagFileIDs().remove(tf.getId());
            t.getLogFileIDs().remove(id);
            tagUpdateService.update(tf);
        }

        return update(lf);
    }

    public FileModel updateLogDatas(String id, List<LogData> logDatas) throws Exception {
        FileModel lf = logGetService.validateAndFindOne(id);
        LogFileData l = (LogFileData) lf.getData();
        logDataScrubberValidatorService.scrubAndValidate(logDatas);
        l.setLogDatas(logDatas);
        return update(lf);
    }
}
