package com.loggerproject.coreservice.endpoint.api.view;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.service.view.create.ViewModelCreateService;
import com.loggerproject.coreservice.service.view.delete.ViewModelDeleteService;
import com.loggerproject.coreservice.service.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.view.update.ViewModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.endpoint.api.GlobalModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/view")
public class ViewModelRestController extends GlobalModelController<ViewModel> {

    @Autowired
    public ViewModelRestController(ViewModelCreateService globalServerCreateService,
                                   ViewModelDeleteService globalServerDeleteService,
                                   ViewModelGetService globalServerGetService,
                                   ViewModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
