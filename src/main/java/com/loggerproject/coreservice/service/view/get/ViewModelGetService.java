package com.loggerproject.coreservice.service.view.get;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.repository.ViewModelRepositoryRestResource;
import com.loggerproject.coreservice.service.view.create.ViewModelCreateService;
import com.loggerproject.coreservice.service.view.delete.ViewModelDeleteService;
import com.loggerproject.coreservice.service.view.update.ViewModelUpdateService;
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
                               @Lazy ViewModelCreateService globalServerCrearteService,
                               @Lazy ViewModelDeleteService globalServerDeleteService,
                               @Lazy ViewModelGetService globalServerGetService,
                               @Lazy ViewModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCrearteService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
