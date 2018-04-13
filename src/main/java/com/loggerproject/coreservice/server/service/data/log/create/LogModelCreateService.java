package com.loggerproject.coreservice.server.service.data.log.create;

import com.loggerproject.coreservice.global.server.service.create.GlobalServerCreateService;
import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.LogDataScrubberValidatorService;
import com.loggerproject.coreservice.server.data.document.tag.TagModel;
import com.loggerproject.coreservice.server.data.repository.LogModelRepository;
import com.loggerproject.coreservice.server.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.server.service.data.directory.update.DirectoryModelUpdateService;
import com.loggerproject.coreservice.server.service.data.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.server.service.data.log.get.LogModelGetService;
import com.loggerproject.coreservice.server.service.data.log.update.LogModelUpdateService;
import com.loggerproject.coreservice.server.service.data.tag.get.TagModelGetService;
import com.loggerproject.coreservice.server.service.data.tag.update.TagModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;

@Service
public class LogModelCreateService extends GlobalServerCreateService<LogModel> {

    @Autowired
    LogModelRepository LogModelRepository;

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
    public LogModelCreateService(LogModelRepository repository,
                                 @Lazy LogModelCreateService globalServerCreateService,
                                 @Lazy LogModelDeleteService globalServerDeleteService,
                                 @Lazy LogModelGetService globalServerGetService,
                                 @Lazy LogModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public LogModel beforeSaveScrubAndValidate(LogModel model) throws Exception {
        if (CollectionUtils.isEmpty(model.getDirectoryIDs())) {
            throw new Exception("LogModel.directoryIDs cannot be empty");
        }

        model.setTagIDs(model.getTagIDs() != null ? model.getTagIDs() : new HashSet());

        directoryModelGetService.validateIds(model.getDirectoryIDs());
        tagModelGetService.validateIds(model.getTagIDs());

        logDataScrubberValidatorService.scrubAndValidate(model.getLogDatas());

        return model;
    }

    public LogModel updateOtherDocuments(LogModel model) throws Exception {
        for (String id : model.getDirectoryIDs()) {
            DirectoryModel d = directoryModelGetService.validateAndFindOne(id);
            d.getLogIDs().add(model.getID());
            directoryModelUpdateService.update(d);
        }

        for (String id : model.getTagIDs()) {
            TagModel t = tagModelGetService.validateAndFindOne(id);
            t.getLogIDs().add(model.getID());
            tagModelUpdateService.update(t);
        }

        return model;
    }

    @Override
    protected LogModel afterCreate(LogModel model) throws Exception {
        model = updateOtherDocuments(model);
        return super.afterCreate(model);
    }
}
