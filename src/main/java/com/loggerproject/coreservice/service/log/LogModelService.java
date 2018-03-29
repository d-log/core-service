package com.loggerproject.coreservice.service.log;

import com.loggerproject.coreservice.data.repository.LogModelRepositoryRestResource;
import com.loggerproject.coreservice.service.directory.DirectoryModelService;
import com.loggerproject.coreservice.data.document.log.LogModel;
import com.loggerproject.coreservice.service.tag.TagModelService;
import com.loggerproject.coreservice.service.viewtemplatetheme.ViewTemplateThemeModelService;
import com.loggerproject.microserviceglobalresource.server.service.GlobalModelServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LogModelService extends GlobalModelServerService<LogModel> {

    @Autowired
    DirectoryModelService directoryModelService;

    @Autowired
    TagModelService tagModelService;

    @Autowired
    ViewTemplateThemeModelService viewTemplateThemeModelService;

    @Autowired
    public LogModelService(LogModelRepositoryRestResource repository) {
        super(repository);
    }

    public void scrubAndValidate(LogModel model) throws Exception {
        model.setDirectoryIDs(model.getDirectoryIDs() != null ? model.getDirectoryIDs() : new ArrayList());
        model.setTagIDs(model.getTagIDs() != null ? model.getTagIDs() : new ArrayList());

        this.directoryModelService.validateIds(model.getDirectoryIDs());
        this.tagModelService.validateIds(model.getTagIDs());

        if (model.getViewTemplateThemeID() != null) {
            this.viewTemplateThemeModelService.validateId(model.getViewTemplateThemeID());
        }
    }
}
