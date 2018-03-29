package com.loggerproject.coreservice.service.viewtemplatetheme.update;

import com.loggerproject.coreservice.data.document.viewtemplatetheme.ViewTemplateThemeModel;
import com.loggerproject.coreservice.data.repository.ViewTemplateThemeModelRepositoryRestResource;
import com.loggerproject.coreservice.service.viewtemplatetheme.create.ViewTemplateThemeModelCreateService;
import com.loggerproject.coreservice.service.viewtemplatetheme.delete.ViewTemplateThemeModelDeleteService;
import com.loggerproject.coreservice.service.viewtemplatetheme.get.ViewTemplateThemeModelGetService;
import com.loggerproject.microserviceglobalresource.server.service.update.GlobalServerUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViewTemplateThemeModelUpdateService extends GlobalServerUpdateService<ViewTemplateThemeModel> {

    @Autowired
    ViewTemplateThemeModelRepositoryRestResource ViewTemplateThemeModelRepositoryRestResource;

    @Autowired
    public ViewTemplateThemeModelUpdateService(ViewTemplateThemeModelRepositoryRestResource repository,
                                               @Lazy ViewTemplateThemeModelCreateService globalServerCreateService,
                                               @Lazy ViewTemplateThemeModelDeleteService globalServerDeleteService,
                                               @Lazy ViewTemplateThemeModelGetService globalServerGetService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService);
    }
}
