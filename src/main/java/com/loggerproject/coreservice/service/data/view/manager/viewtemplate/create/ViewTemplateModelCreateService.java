package com.loggerproject.coreservice.service.data.view.manager.viewtemplate.create;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateModel;
import com.loggerproject.coreservice.data.repository.ViewTemplateModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.view.manager.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.data.view.manager.view.update.ViewModelUpdateService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.ViewTemplateModelUtilService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.delete.ViewTemplateModelDeleteService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.get.ViewTemplateModelGetService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.update.ViewTemplateModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.create.GlobalServerCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViewTemplateModelCreateService extends GlobalServerCreateService<ViewTemplateModel> {

    @Autowired
    ViewModelGetService viewModelGetService;

    @Autowired
    ViewModelUpdateService viewModelUpdateService;

    @Autowired
    ViewTemplateModelUtilService viewTemplateModelUtilService;

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
        model.setHtml(viewTemplateModelUtilService.scrubAndValidateHTML(model.getHtml()));
        model.setJs(viewTemplateModelUtilService.scrubAndValidateJS(model.getJs()));
        model.setCss(viewTemplateModelUtilService.scrubAndValidateCSS(model.getCss()));
    }

    protected void updateOtherDocuments(ViewTemplateModel model) throws Exception {
        ViewModel view = viewModelGetService.findOne(model.getID());
        view.getOtherViewTemplateIDs().add(model.getID());
        viewModelUpdateService.update(view);
    }

    @Override
    protected void beforeSave(ViewTemplateModel model) throws Exception {
        scrubAndValidate(model);
        super.beforeSave(model);
    }

    @Override
    protected void afterSave(ViewTemplateModel model) throws Exception {
        updateOtherDocuments(model);
        super.afterSave(model);
    }
}
