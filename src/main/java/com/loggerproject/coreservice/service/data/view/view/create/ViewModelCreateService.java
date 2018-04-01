package com.loggerproject.coreservice.service.data.view.view.create;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.repository.ViewModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.view.view.ViewModelUtilService;
import com.loggerproject.coreservice.service.data.view.view.delete.ViewModelDeleteService;
import com.loggerproject.coreservice.service.data.view.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.data.view.view.update.ViewModelUpdateService;
import com.loggerproject.coreservice.service.data.view.viewtemplate.get.ViewTemplateModelGetService;
import com.loggerproject.microserviceglobalresource.server.service.create.GlobalServerCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViewModelCreateService extends GlobalServerCreateService<ViewModel> {

    @Autowired
    ViewModelRepositoryRestResource viewModelRepositoryRestResource;

    @Autowired
    ViewTemplateModelGetService viewTemplateModelGetService;

    @Autowired
    ViewModelUtilService viewModelUtilService;

    @Autowired
    public ViewModelCreateService(ViewModelRepositoryRestResource repository,
                                  @Lazy ViewModelCreateService globalServerCreateService,
                                  @Lazy ViewModelDeleteService globalServerDeleteService,
                                  @Lazy ViewModelGetService globalServerGetService,
                                  @Lazy ViewModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
