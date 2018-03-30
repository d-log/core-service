package com.loggerproject.coreservice.service.view.delete;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.repository.ViewModelRepositoryRestResource;
import com.loggerproject.coreservice.service.view.create.ViewModelCreateService;
import com.loggerproject.coreservice.service.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.view.update.ViewModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.delete.GlobalServerDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViewModelDeleteService extends GlobalServerDeleteService<ViewModel> {

    @Autowired
    ViewModelRepositoryRestResource ViewModelRepositoryRestResource;

    @Autowired
    public ViewModelDeleteService(ViewModelRepositoryRestResource repository,
                                  @Lazy ViewModelCreateService globalServerCrearteService,
                                  @Lazy ViewModelDeleteService globalServerDeleteService,
                                  @Lazy ViewModelGetService globalServerGetService,
                                  @Lazy ViewModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCrearteService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
