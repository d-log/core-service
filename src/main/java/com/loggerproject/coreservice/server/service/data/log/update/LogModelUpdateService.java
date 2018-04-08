package com.loggerproject.coreservice.server.service.data.log.update;

import com.loggerproject.coreservice.global.server.service.update.GlobalServerUpdateService;
import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.LogData;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.LogDataScrubberValidatorService;
import com.loggerproject.coreservice.server.data.document.tag.TagModel;
import com.loggerproject.coreservice.server.data.repository.LogModelRepository;
import com.loggerproject.coreservice.server.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.server.service.data.directory.update.DirectoryModelUpdateService;
import com.loggerproject.coreservice.server.service.data.log.create.LogModelCreateService;
import com.loggerproject.coreservice.server.service.data.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.server.service.data.log.get.LogModelGetService;
import com.loggerproject.coreservice.server.service.data.tag.get.TagModelGetService;
import com.loggerproject.coreservice.server.service.data.tag.update.TagModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@SuppressWarnings(value = "unchecked")
public class LogModelUpdateService extends GlobalServerUpdateService<LogModel> {

    @Autowired
    LogModelRepository LogModelRepository;

    @Autowired
    LogModelGetService logModelGetService;

    @Autowired
    DirectoryModelGetService directoryModelGetService;

    @Autowired
    DirectoryModelUpdateService directoryModelUpdateService;

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    TagModelUpdateService tagModelUpdateService;

    @Autowired
    LogDataScrubberValidatorService logDataScrubberValidatorService;

    @Autowired
    public LogModelUpdateService(LogModelRepository repository,
                                 @Lazy LogModelCreateService globalServerCreateService,
                                 @Lazy LogModelDeleteService globalServerDeleteService,
                                 @Lazy LogModelGetService globalServerGetService,
                                 @Lazy LogModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public LogModel bindUnbindDirectories(String logID, List<String> bindDirectoryIDs, List<String> unbindDirectoryIDs) throws Exception {
        LogModel log = logModelGetService.validateAndFindOne(logID);

        List<DirectoryModel> bindDirectories = new ArrayList<>();
        List<DirectoryModel> unbindDirectories = new ArrayList<>();

        for (String directoryID : bindDirectoryIDs) {
            DirectoryModel directory = directoryModelGetService.validateAndFindOne(directoryID);
            bindDirectories.add(directory);
        }
        for (String directoryID : unbindDirectoryIDs) {
            DirectoryModel directory = directoryModelGetService.validateAndFindOne(directoryID);
            unbindDirectories.add(directory);
        }

        for (DirectoryModel directory : bindDirectories) {
            log.getDirectoryIDs().add(directory.getID());
            directory.getLogIDs().add(logID);
            directoryModelUpdateService.update(directory);
        }
        for (DirectoryModel directory : unbindDirectories) {
            log.getDirectoryIDs().remove(directory.getID());
            directory.getLogIDs().remove(logID);
            directoryModelUpdateService.update(directory);
        }

        return update(log);
    }

    public LogModel bindUnbindTags(String logID, List<String> bindTagIDs, List<String> unbindTagIDs) throws Exception {
        LogModel log = logModelGetService.validateAndFindOne(logID);

        List<TagModel> bindTags = new ArrayList<>();
        List<TagModel> unbindTags = new ArrayList<>();

        for (String tagID : bindTagIDs) {
            TagModel tag = tagModelGetService.validateAndFindOne(tagID);
            bindTags.add(tag);
        }
        for (String tagID : unbindTagIDs) {
            TagModel tag = tagModelGetService.validateAndFindOne(tagID);
            unbindTags.add(tag);
        }

        for (TagModel tag : bindTags) {
            log.getTagIDs().add(tag.getID());
            tag.getLogIDs().add(logID);
            tagModelUpdateService.update(tag);
        }
        for (TagModel tag : unbindTags) {
            log.getTagIDs().remove(tag.getID());
            tag.getLogIDs().remove(logID);
            tagModelUpdateService.update(tag);
        }

        return update(log);
    }

    public LogModel updateLogDatas(String id, List<LogData> logDatas) throws Exception {
        LogModel log = logModelGetService.validateAndFindOne(id);
        logDataScrubberValidatorService.scrubAndValidate(logDatas);
        log.setLogDatas(logDatas);
        return update(log);
    }
}
