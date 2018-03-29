package com.loggerproject.coreservice.service.viewtemplatetheme.create;

import com.loggerproject.coreservice.data.document.viewtemplatetheme.ViewTemplateThemeModel;
import com.loggerproject.coreservice.data.repository.ViewTemplateThemeModelRepositoryRestResource;
import com.loggerproject.coreservice.service.viewtemplatetheme.delete.ViewTemplateThemeModelDeleteService;
import com.loggerproject.coreservice.service.viewtemplatetheme.get.ViewTemplateThemeModelGetService;
import com.loggerproject.coreservice.service.viewtemplatetheme.update.ViewTemplateThemeModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.create.GlobalServerCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViewTemplateThemeModelCreateService extends GlobalServerCreateService<ViewTemplateThemeModel> {

    @Autowired
    ViewTemplateThemeModelRepositoryRestResource ViewTemplateThemeModelRepositoryRestResource;

    @Autowired
    public ViewTemplateThemeModelCreateService(ViewTemplateThemeModelRepositoryRestResource repository,
                                               @Lazy ViewTemplateThemeModelDeleteService globalServerDeleteService,
                                               @Lazy ViewTemplateThemeModelGetService globalServerGetService,
                                               @Lazy ViewTemplateThemeModelUpdateService globalServerUpdateService) {
        super(repository, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public void scrubAndValidate(ViewTemplateThemeModel model) throws Exception {
        // TODO
    }

    @Override
    protected void beforeSave(ViewTemplateThemeModel model) throws Exception {
        scrubAndValidate(model);
        super.beforeSave(model);
    }
}
