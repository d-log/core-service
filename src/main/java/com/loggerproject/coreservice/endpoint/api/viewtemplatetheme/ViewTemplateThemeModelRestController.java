package com.loggerproject.coreservice.endpoint.api.viewtemplatetheme;

import com.loggerproject.coreservice.data.document.viewtemplatetheme.ViewTemplateThemeModel;
import com.loggerproject.coreservice.service.data.view.viewtemplatetheme.create.ViewTemplateThemeModelCreateService;
import com.loggerproject.coreservice.service.data.view.viewtemplatetheme.delete.ViewTemplateThemeModelDeleteService;
import com.loggerproject.coreservice.service.data.view.viewtemplatetheme.get.ViewTemplateThemeModelGetService;
import com.loggerproject.coreservice.service.data.view.viewtemplatetheme.update.ViewTemplateThemeModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.endpoint.api.GlobalModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/view-template-theme")
public class ViewTemplateThemeModelRestController extends GlobalModelController<ViewTemplateThemeModel> {

    @Autowired
    public ViewTemplateThemeModelRestController(ViewTemplateThemeModelCreateService globalServerCreateService,
                                                ViewTemplateThemeModelDeleteService globalServerDeleteService,
                                                ViewTemplateThemeModelGetService globalServerGetService,
                                                ViewTemplateThemeModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}