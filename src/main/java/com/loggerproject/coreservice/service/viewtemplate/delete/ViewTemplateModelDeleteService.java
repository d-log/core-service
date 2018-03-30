package com.loggerproject.coreservice.service.viewtemplate.delete;

import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateModel;
import com.loggerproject.coreservice.data.repository.ViewTemplateModelRepositoryRestResource;
import com.loggerproject.coreservice.service.viewtemplate.create.ViewTemplateModelCreateService;
import com.loggerproject.coreservice.service.viewtemplate.get.ViewTemplateModelGetService;
import com.loggerproject.coreservice.service.viewtemplate.update.ViewTemplateModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.delete.GlobalServerDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViewTemplateModelDeleteService extends GlobalServerDeleteService<ViewTemplateModel> {

    @Autowired
    ViewTemplateModelRepositoryRestResource ViewTemplateModelRepositoryRestResource;

    @Autowired
    public ViewTemplateModelDeleteService(ViewTemplateModelRepositoryRestResource repository,
                                          @Lazy ViewTemplateModelCreateService globalServerCreateService,
                                          @Lazy ViewTemplateModelDeleteService globalServerDeleteService,
                                          @Lazy ViewTemplateModelGetService globalServerGetService,
                                          @Lazy ViewTemplateModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
