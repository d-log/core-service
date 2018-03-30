package com.loggerproject.coreservice.service.log.create;

import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.data.document.log.LogModel;
import com.loggerproject.coreservice.data.document.log.ViewData;
import com.loggerproject.coreservice.data.document.tag.TagModel;
import com.loggerproject.coreservice.data.repository.LogModelRepositoryRestResource;
import com.loggerproject.coreservice.service.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.service.directory.update.DirectoryModelUpdateService;
import com.loggerproject.coreservice.service.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.service.log.get.LogModelGetService;
import com.loggerproject.coreservice.service.log.update.LogModelUpdateService;
import com.loggerproject.coreservice.service.tag.get.TagModelGetService;
import com.loggerproject.coreservice.service.tag.update.TagModelUpdateService;
import com.loggerproject.coreservice.service.viewtemplatetheme.get.ViewTemplateThemeModelGetService;
import com.loggerproject.microserviceglobalresource.server.service.create.GlobalServerCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class LogModelCreateService extends GlobalServerCreateService<LogModel> {

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
    ViewTemplateThemeModelGetService viewTemplateThemeModelGetService;

    @Autowired
    ViewDataService viewDataService;

    @Autowired
    public LogModelCreateService(LogModelRepositoryRestResource repository,
                                 @Lazy LogModelCreateService globalServerCreateService,
                                 @Lazy LogModelDeleteService globalServerDeleteService,
                                 @Lazy LogModelGetService globalServerGetService,
                                 @Lazy LogModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public void scrubAndValidate(LogModel model) throws Exception {
        model.setDirectoryIDs(model.getDirectoryIDs() != null ? model.getDirectoryIDs() : new HashSet());
        model.setTagIDs(model.getTagIDs() != null ? model.getTagIDs() : new HashSet());

        directoryModelGetService.validateIds(model.getDirectoryIDs());
        tagModelGetService.validateIds(model.getTagIDs());

        if (model.getViewTemplateThemeID() != null) {
            this.viewTemplateThemeModelGetService.validateId(model.getViewTemplateThemeID());
        }

        for (ViewData viewData : model.getViewDatas()) {
            viewDataService.scrubAndValidate(viewData);
        }
    }

    public void updateOtherDocuments(LogModel model) throws Exception {
        for (String id : model.getDirectoryIDs()) {
            DirectoryModel d = directoryModelGetService.validateAndFindOne(id);
            d.getLogIDs().add(model.getID());
            directoryModelUpdateService.update(d.getID(), d);
        }

        for (String id : model.getTagIDs()) {
            TagModel t = tagModelGetService.validateAndFindOne(id);
            t.getLogIDs().add(model.getID());
            tagModelUpdateService.update(t.getID(), t);
        }
    }

    @Override
    protected void beforeSave(LogModel model) throws Exception {
        scrubAndValidate(model);
        super.beforeSave(model);
    }

    @Override
    protected void afterSave(LogModel model) throws Exception {
        updateOtherDocuments(model);
        super.beforeSave(model);
    }
}
