package com.loggerproject.coreservice.service.data.view.manager.viewtemplate.delete;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateModel;
import com.loggerproject.coreservice.data.repository.ViewTemplateModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.view.manager.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.data.view.manager.view.update.ViewModelUpdateService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.create.ViewTemplateModelCreateService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.get.ViewTemplateModelGetService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.update.ViewTemplateModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.delete.GlobalServerDeleteService;
import com.loggerproject.microserviceglobalresource.server.service.delete.model.ModelBoundedToLogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ViewTemplateModelDeleteService extends GlobalServerDeleteService<ViewTemplateModel> {

    @Autowired
    ViewTemplateModelRepositoryRestResource ViewTemplateModelRepositoryRestResource;

    @Autowired
    ViewModelGetService viewModelGetService;

    @Autowired
    ViewModelUpdateService viewModelUpdateService;

    @Autowired
    public ViewTemplateModelDeleteService(ViewTemplateModelRepositoryRestResource repository,
                                          @Lazy ViewTemplateModelCreateService globalServerCreateService,
                                          @Lazy ViewTemplateModelDeleteService globalServerDeleteService,
                                          @Lazy ViewTemplateModelGetService globalServerGetService,
                                          @Lazy ViewTemplateModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public void validateDelete(ViewTemplateModel model) throws Exception {
        ViewModel view = viewModelGetService.findOne(model.getViewID());
        if (view.getDefaultViewTemplateID().equals(model.getID())) {
            // TODO throw different exception bc 'ViewTemplateModel is default of ViewModel'
            // GlobalServerDeleteService.deleteAll requires ModelBoundedToLogException to be thrown in order to catch it
            throw new ModelBoundedToLogException(model.getID(), Collections.singletonList(view.getID()));
        }
    }

    @Override
    protected void beforeDelete(ViewTemplateModel model) throws Exception {
        validateDelete(model);
        super.beforeDelete(model);
    }

    @Override
    protected void afterDelete(ViewTemplateModel model) throws Exception {
        ViewModel view = viewModelGetService.findOne(model.getViewID());
        view.getOtherViewTemplateIDs().remove(model.getID());
        viewModelUpdateService.update(view);
    }

}
