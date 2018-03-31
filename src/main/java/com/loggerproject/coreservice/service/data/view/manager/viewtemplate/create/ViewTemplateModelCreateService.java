package com.loggerproject.coreservice.service.data.view.manager.viewtemplate.create;

import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateModel;
import com.loggerproject.coreservice.data.repository.ViewTemplateModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.view.manager.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.delete.ViewTemplateModelDeleteService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.get.ViewTemplateModelGetService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.update.ViewTemplateModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.create.GlobalServerCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ViewTemplateModelCreateService extends GlobalServerCreateService<ViewTemplateModel> {

    @Autowired
    ViewTemplateModelRepositoryRestResource ViewTemplateModelRepositoryRestResource;

    @Autowired
    ViewModelGetService viewModelGetService;

    @Autowired
    public ViewTemplateModelCreateService(ViewTemplateModelRepositoryRestResource repository,
                                          @Lazy ViewTemplateModelCreateService globalServerCreateService,
                                          @Lazy ViewTemplateModelDeleteService globalServerDeleteService,
                                          @Lazy ViewTemplateModelGetService globalServerGetService,
                                          @Lazy ViewTemplateModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public void scrubAndValidate(ViewTemplateModel model) throws Exception {
        viewModelGetService.validateId(model.getViewID());

        this.scrubAndValidateHTML(model);
        this.scrubAndValidateJS(model);
        this.scrubAndValidateCSS(model);
    }

    private void scrubAndValidateHTML(ViewTemplateModel model) throws Exception {
        if (StringUtils.isEmpty(model.getHtml())) {
            throw new Exception("ViewTemplateModel.html cannot be blank");
        }
    }

    private void scrubAndValidateJS(ViewTemplateModel model) throws Exception {
        if (model.getJs() == null) {
            model.setJs("");
        }
    }

    private void scrubAndValidateCSS(ViewTemplateModel model) throws Exception {
        if (model.getCss() == null) {
            model.setCss("");
        }
    }

    @Override
    protected void beforeSave(ViewTemplateModel model) throws Exception {
        scrubAndValidate(model);
        super.beforeSave(model);
    }
}
