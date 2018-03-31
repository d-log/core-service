package com.loggerproject.coreservice.endpoint.api.viewtemplate;

import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateModel;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.create.ViewTemplateModelCreateService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.delete.ViewTemplateModelDeleteService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.get.ViewTemplateModelGetService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.update.ViewTemplateModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.endpoint.api.GlobalModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/view-template")
public class ViewTemplateModelRestController extends GlobalModelController<ViewTemplateModel> {

    @Autowired
    public ViewTemplateModelRestController(ViewTemplateModelCreateService globalServerCreateService,
                                           ViewTemplateModelDeleteService globalServerDeleteService,
                                           ViewTemplateModelGetService globalServerGetService,
                                           ViewTemplateModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}