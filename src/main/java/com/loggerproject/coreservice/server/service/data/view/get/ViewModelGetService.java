package com.loggerproject.coreservice.server.service.data.view.get;

import com.loggerproject.coreservice.server.data.document.view.ViewModel;
import com.loggerproject.coreservice.server.data.repository.ViewModelRepository;
import com.loggerproject.coreservice.server.service.data.view.create.ViewModelCreateService;
import com.loggerproject.coreservice.server.service.data.view.delete.ViewModelDeleteService;
import com.loggerproject.coreservice.server.service.data.view.update.ViewModelUpdateService;
import com.loggerproject.coreservice.global.server.service.get.GlobalServerGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViewModelGetService extends GlobalServerGetService<ViewModel> {

    @Autowired
    ViewModelRepository ViewModelRepository;

    @Autowired
    public ViewModelGetService(ViewModelRepository repository,
                               @Lazy ViewModelCreateService globalServerCreateService,
                               @Lazy ViewModelDeleteService globalServerDeleteService,
                               @Lazy ViewModelGetService globalServerGetService,
                               @Lazy ViewModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
