package com.loggerproject.coreservice.service.data.view.viewtemplate.delete;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateModel;
import com.loggerproject.coreservice.data.repository.ViewTemplateModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.view.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.data.view.view.update.ViewModelUpdateService;
import com.loggerproject.coreservice.service.data.view.viewtemplate.create.ViewTemplateModelCreateService;
import com.loggerproject.coreservice.service.data.view.viewtemplate.get.ViewTemplateModelGetService;
import com.loggerproject.coreservice.service.data.view.viewtemplate.update.ViewTemplateModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.delete.GlobalServerDeleteService;
import com.loggerproject.microserviceglobalresource.server.service.delete.model.ValidateDeleteModelException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

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
        if (view != null) {
            if (view.getDefaultViewTemplateID().equals(model.getID())) {
                throw new ValidateDeleteModelException(model.getID(), "ViewTemplateModel: '" + model.getID() + "' is default of ViewModel:'" + view.getID() + "'");
            }
        }
    }

    @Override
    protected ViewTemplateModel beforeDelete(ViewTemplateModel model) throws Exception {
        validateDelete(model);
        return super.beforeDelete(model);
    }

    protected ViewTemplateModel updateOtherDocuments(ViewTemplateModel model) throws Exception {
        ViewModel view = viewModelGetService.findOne(model.getViewID());
        if (view != null) {
            view.getOtherViewTemplateIDs().remove(model.getID());
            viewModelUpdateService.update(view);
        }
        return model;
    }

    @Override
    protected ViewTemplateModel afterDelete(ViewTemplateModel model) throws Exception {
        model = updateOtherDocuments(model);
        return super.afterDelete(model);
    }
}
