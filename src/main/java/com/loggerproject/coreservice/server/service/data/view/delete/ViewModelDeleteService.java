package com.loggerproject.coreservice.server.service.data.view.delete;

import com.loggerproject.coreservice.server.data.document.view.ViewModel;
import com.loggerproject.coreservice.server.data.repository.ViewModelRepository;
import com.loggerproject.coreservice.server.service.data.view.create.ViewModelCreateService;
import com.loggerproject.coreservice.server.service.data.view.get.ViewModelGetService;
import com.loggerproject.coreservice.server.service.data.view.update.ViewModelUpdateService;
import com.loggerproject.coreservice.global.server.service.delete.GlobalServerDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViewModelDeleteService extends GlobalServerDeleteService<ViewModel> {

    @Autowired
    ViewModelRepository ViewModelRepository;

    @Autowired
    public ViewModelDeleteService(ViewModelRepository repository,
                                  @Lazy ViewModelCreateService globalServerCreateService,
                                  @Lazy ViewModelDeleteService globalServerDeleteService,
                                  @Lazy ViewModelGetService globalServerGetService,
                                  @Lazy ViewModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
