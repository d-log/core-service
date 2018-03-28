package com.loggerproject.coreservice.data.viewtemplatetheme.service;

import com.loggerproject.coreservice.data.viewtemplatetheme.endpoint.ViewTemplateThemeModelRepositoryRestResource;
import com.loggerproject.coreservice.data.viewtemplatetheme.model.ViewTemplateThemeModel;
import com.loggerproject.microserviceglobalresource.server.service.GlobalServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewTemplateThemeModelService extends GlobalServerService<ViewTemplateThemeModel> {

    @Autowired
    public ViewTemplateThemeModelService(ViewTemplateThemeModelRepositoryRestResource repository) {
        super(repository);
    }

    protected ViewTemplateThemeModel scrubAndValidate(ViewTemplateThemeModel model) throws Exception {
        // TODO do it
        return model;
    }
}



