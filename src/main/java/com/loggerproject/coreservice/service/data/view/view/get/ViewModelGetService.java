package com.loggerproject.coreservice.service.data.view.view.get;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.repository.ViewModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.view.view.create.ViewModelCreateService;
import com.loggerproject.coreservice.service.data.view.view.delete.ViewModelDeleteService;
import com.loggerproject.coreservice.service.data.view.view.update.ViewModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.get.GlobalServerGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViewModelGetService extends GlobalServerGetService<ViewModel> {

    @Autowired
    ViewModelRepositoryRestResource ViewModelRepositoryRestResource;

    @Autowired
    public ViewModelGetService(ViewModelRepositoryRestResource repository,
                               @Lazy ViewModelCreateService globalServerCreateService,
                               @Lazy ViewModelDeleteService globalServerDeleteService,
                               @Lazy ViewModelGetService globalServerGetService,
                               @Lazy ViewModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}