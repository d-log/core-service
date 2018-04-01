package com.loggerproject.coreservice.service.data.view.viewtemplatetheme.delete;

import com.loggerproject.coreservice.data.document.viewtemplatetheme.ViewTemplateThemeModel;
import com.loggerproject.coreservice.data.repository.ViewTemplateThemeModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.view.viewtemplatetheme.create.ViewTemplateThemeModelCreateService;
import com.loggerproject.coreservice.service.data.view.viewtemplatetheme.get.ViewTemplateThemeModelGetService;
import com.loggerproject.coreservice.service.data.view.viewtemplatetheme.update.ViewTemplateThemeModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.delete.GlobalServerDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViewTemplateThemeModelDeleteService extends GlobalServerDeleteService<ViewTemplateThemeModel> {

    @Autowired
    ViewTemplateThemeModelRepositoryRestResource ViewTemplateThemeModelRepositoryRestResource;

    @Autowired
    public ViewTemplateThemeModelDeleteService(ViewTemplateThemeModelRepositoryRestResource repository,
                                               @Lazy ViewTemplateThemeModelCreateService globalServerCreateService,
                                               @Lazy ViewTemplateThemeModelDeleteService globalServerDeleteService,
                                               @Lazy ViewTemplateThemeModelGetService globalServerGetService,
                                               @Lazy ViewTemplateThemeModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }


}
