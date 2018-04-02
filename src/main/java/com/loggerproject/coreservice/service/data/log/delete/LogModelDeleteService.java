package com.loggerproject.coreservice.service.data.log.delete;

import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.data.document.log.LogModel;
import com.loggerproject.coreservice.data.document.tag.TagModel;
import com.loggerproject.coreservice.data.repository.LogModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.service.data.directory.update.DirectoryModelUpdateService;
import com.loggerproject.coreservice.service.data.log.create.LogModelCreateService;
import com.loggerproject.coreservice.service.data.log.get.LogModelGetService;
import com.loggerproject.coreservice.service.data.log.update.LogModelUpdateService;
import com.loggerproject.coreservice.service.data.tag.get.TagModelGetService;
import com.loggerproject.coreservice.service.data.tag.update.TagModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.delete.GlobalServerDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LogModelDeleteService extends GlobalServerDeleteService<LogModel> {

    @Autowired
    LogModelRepositoryRestResource LogModelRepositoryRestResource;

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    TagModelUpdateService tagModelUpdateService;

    @Autowired
    DirectoryModelGetService directoryModelGetService;

    @Autowired
    DirectoryModelUpdateService directoryModelUpdateService;

    @Autowired
    LogModelGetService logModelGetService;

    @Autowired
    public LogModelDeleteService(LogModelRepositoryRestResource repository,
                                 @Lazy LogModelCreateService globalServerCreateService,
                                 @Lazy LogModelDeleteService globalServerDeleteService,
                                 @Lazy LogModelGetService globalServerGetService,
                                 @Lazy LogModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public LogModel validateDelete(LogModel model) {
        return model;
    }

    public LogModel updateDocuments(LogModel model) throws Exception {
        for (String dID : model.getDirectoryIDs()) {
            DirectoryModel d = directoryModelGetService.findOne(dID);
            if (d != null) {
                d.getLogIDs().remove(model.getID());
                directoryModelUpdateService.update(d);
            }
        }

        for (String tID : model.getTagIDs()) {
            TagModel t = tagModelGetService.findOne(tID);
            if (t != null) {
                t.getLogIDs().remove(model.getID());
                tagModelUpdateService.update(t);
            }
        }

        return model;
    }

    @Override
    public LogModel beforeDelete(LogModel model) throws Exception {
        model = validateDelete(model);
        model = updateDocuments(model);
        return super.beforeDelete(model);
    }
}
