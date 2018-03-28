package com.loggerproject.coreservice.data.log.service;

import com.loggerproject.coreservice.data.directory.service.DirectoryModelService;
import com.loggerproject.coreservice.data.log.endpoint.LogModelRepositoryRestResource;
import com.loggerproject.coreservice.data.log.model.LogModel;
import com.loggerproject.coreservice.data.log.model.ViewData;
import com.loggerproject.coreservice.data.tag.service.TagModelService;
import com.loggerproject.coreservice.data.viewtemplatetheme.service.ViewTemplateThemeModelService;
import com.loggerproject.microserviceglobalresource.server.service.GlobalServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LogModelService extends GlobalServerService<LogModel> {

    @Autowired
    ViewDataService viewDataService;

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

    protected LogModel scrubAndValidate(LogModel model) throws Exception {
        model.setDirectoryIDs(model.getDirectoryIDs() != null ? model.getDirectoryIDs() : new ArrayList());
        model.setTagIDs(model.getTagIDs() != null ? model.getTagIDs() : new ArrayList());

        for (ViewData viewData : model.getViewDatas()) {
            viewDataService.scrubAndValidate(viewData);
        }

        directoryModelService.validateIds(model.getDirectoryIDs());
        tagModelService.validateIds(model.getTagIDs());

        if (model.getViewTemplateThemeID() != null) {
            viewTemplateThemeModelService.validateId(model.getViewTemplateThemeID());
        }

        return model;
    }
}
