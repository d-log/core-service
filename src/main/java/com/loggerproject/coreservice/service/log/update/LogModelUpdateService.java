package com.loggerproject.coreservice.service.log.update;

import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.data.document.log.LogModel;
import com.loggerproject.coreservice.data.document.tag.TagModel;
import com.loggerproject.coreservice.data.repository.LogModelRepositoryRestResource;
import com.loggerproject.coreservice.service.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.service.directory.update.DirectoryModelUpdateService;
import com.loggerproject.coreservice.service.log.create.LogModelCreateService;
import com.loggerproject.coreservice.service.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.service.log.get.LogModelGetService;
import com.loggerproject.coreservice.service.tag.get.TagModelGetService;
import com.loggerproject.coreservice.service.tag.update.TagModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.update.GlobalServerUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LogModelUpdateService extends GlobalServerUpdateService<LogModel> {

    @Autowired
    LogModelRepositoryRestResource LogModelRepositoryRestResource;

    @Autowired
    DirectoryModelGetService directoryModelGetService;

    @Autowired
    DirectoryModelUpdateService directoryModelUpdateService;

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    TagModelUpdateService tagModelUpdateService;

    @Autowired
    public LogModelUpdateService(LogModelRepositoryRestResource repository,
                                 @Lazy LogModelCreateService globalServerCreateService,
                                 @Lazy LogModelDeleteService globalServerDeleteService,
                                 @Lazy LogModelGetService globalServerGetService,
                                 @Lazy LogModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public void bindFromDirectoryToDirectory(String logID, String oldDirectoryID, String newDirectoryID) throws Exception {
        LogModel log = (LogModel)globalServerGetService.validateAndFindOne(logID);
        DirectoryModel oldDirectory = directoryModelGetService.validateAndFindOne(oldDirectoryID);
        DirectoryModel newDirectory = directoryModelGetService.validateAndFindOne(newDirectoryID);

        log.getDirectoryIDs().remove(oldDirectoryID);
        log.getDirectoryIDs().add(newDirectoryID);

        oldDirectory.getLogIDs().remove(logID);
        newDirectory.getLogIDs().add(logID);

        globalServerUpdateService.update(logID, log);
        directoryModelUpdateService.update(oldDirectoryID, oldDirectory);
        directoryModelUpdateService.update(newDirectoryID, newDirectory);
    }

    public void bindToDirectory(String logID, String directoryID) throws Exception {
        LogModel log = (LogModel)globalServerGetService.validateAndFindOne(logID);
        DirectoryModel directory = directoryModelGetService.validateAndFindOne(directoryID);

        log.getDirectoryIDs().add(directoryID);
        directory.getLogIDs().add(logID);

        globalServerUpdateService.update(logID, log);
        directoryModelUpdateService.update(directoryID, directory);
    }

    public void bindToTag(String logID, String tagID) throws Exception {
        LogModel log = (LogModel)globalServerGetService.validateAndFindOne(logID);
        TagModel tag = tagModelGetService.validateAndFindOne(tagID);

        log.getTagIDs().add(tagID);
        tag.getLogIDs().add(logID);

        globalServerUpdateService.update(logID, log);
        tagModelUpdateService.update(tagID, tag);
    }

    public void unbindTag(String logID, String tagID) throws Exception {
        LogModel log = (LogModel)globalServerGetService.validateAndFindOne(logID);
        TagModel tag = tagModelGetService.validateAndFindOne(tagID);

        log.getTagIDs().remove(tagID);
        tag.getLogIDs().remove(logID);

        globalServerUpdateService.update(logID, log);
        tagModelUpdateService.update(tagID, tag);
    }
}
