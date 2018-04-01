package com.loggerproject.coreservice.service.data.view.viewtemplate.update;

import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateModel;
import com.loggerproject.coreservice.data.repository.ViewTemplateModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.view.viewtemplate.ViewTemplateModelUtilService;
import com.loggerproject.coreservice.service.data.view.viewtemplate.create.ViewTemplateModelCreateService;
import com.loggerproject.coreservice.service.data.view.viewtemplate.delete.ViewTemplateModelDeleteService;
import com.loggerproject.coreservice.service.data.view.viewtemplate.get.ViewTemplateModelGetService;
import com.loggerproject.microserviceglobalresource.server.service.update.GlobalServerUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViewTemplateModelUpdateService extends GlobalServerUpdateService<ViewTemplateModel> {

    @Autowired
    ViewTemplateModelRepositoryRestResource ViewTemplateModelRepositoryRestResource;

    @Autowired
    ViewTemplateModelUtilService viewTemplateModelUtilService;

    @Autowired
    ViewTemplateModelGetService viewTemplateModelGetService;

    @Autowired
    public ViewTemplateModelUpdateService(ViewTemplateModelRepositoryRestResource repository,
                                          @Lazy ViewTemplateModelCreateService globalServerCreateService,
                                          @Lazy ViewTemplateModelDeleteService globalServerDeleteService,
                                          @Lazy ViewTemplateModelGetService globalServerGetService,
                                          @Lazy ViewTemplateModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public ViewTemplateModel updateHTMLJSCSS(String id, String html, String js, String css) throws Exception {
        ViewTemplateModel model = viewTemplateModelGetService.validateAndFindOne(id);

        html = viewTemplateModelUtilService.scrubAndValidateHTML(html);
        js = viewTemplateModelUtilService.scrubAndValidateJS(js);
        css = viewTemplateModelUtilService.scrubAndValidateCSS(css);

        model.setHtml(html);
        model.setJs(js);
        model.setCss(css);

        return update(model);
    }
}
