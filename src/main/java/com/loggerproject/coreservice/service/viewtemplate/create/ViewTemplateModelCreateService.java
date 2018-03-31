package com.loggerproject.coreservice.service.viewtemplate.create;

import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateCSS;
import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateJS;
import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateModel;
import com.loggerproject.coreservice.data.repository.ViewTemplateModelRepositoryRestResource;
import com.loggerproject.coreservice.service.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.viewtemplate.delete.ViewTemplateModelDeleteService;
import com.loggerproject.coreservice.service.viewtemplate.get.ViewTemplateModelGetService;
import com.loggerproject.coreservice.service.viewtemplate.update.ViewTemplateModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.create.GlobalServerCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

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
        model.setName(model.getName() != null ? model.getName() : "");
        viewModelGetService.validateId(model.getViewID());

        this.scrubAndValidateHTML(model);
        this.scrubAndValidateJS(model);
        this.scrubAndValidateCSS(model);
    }

    private void scrubAndValidateHTML(ViewTemplateModel model) throws Exception {
        Assert.notNull(model.getHtml(), "ViewTemplateModel.viewTemplateHtml cannot be null");
        Assert.hasLength(model.getHtml().getCode(), "ViewTemplateModel.viewTemplateHtml.code cannot be blank");
    }

    private void scrubAndValidateJS(ViewTemplateModel model) throws Exception {
        if (model.getJs() == null) {
            model.setJs(new ViewTemplateJS());
        }
    }

    private void scrubAndValidateCSS(ViewTemplateModel model) throws Exception {
        if (model.getCss() == null) {
            model.setCss(new ViewTemplateCSS());
        }
    }

    @Override
    protected void beforeSave(ViewTemplateModel model) throws Exception {
        scrubAndValidate(model);
        super.beforeSave(model);
    }
}
