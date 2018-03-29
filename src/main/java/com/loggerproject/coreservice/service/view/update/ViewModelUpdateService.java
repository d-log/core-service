package com.loggerproject.coreservice.service.view.update;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.repository.ViewModelRepositoryRestResource;
import com.loggerproject.coreservice.service.view.create.ViewModelCreateService;
import com.loggerproject.coreservice.service.view.delete.ViewModelDeleteService;
import com.loggerproject.coreservice.service.view.get.ViewModelGetService;
import com.loggerproject.microserviceglobalresource.server.service.update.GlobalServerUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViewModelUpdateService extends GlobalServerUpdateService<ViewModel> {

    @Autowired
    ViewModelRepositoryRestResource ViewModelRepositoryRestResource;

    @Autowired
    public ViewModelUpdateService(ViewModelRepositoryRestResource repository,
                                  @Lazy ViewModelCreateService globalServerCreateService,
                                  @Lazy ViewModelDeleteService globalServerDeleteService,
                                  @Lazy ViewModelGetService globalServerGetService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService);
    }
}
