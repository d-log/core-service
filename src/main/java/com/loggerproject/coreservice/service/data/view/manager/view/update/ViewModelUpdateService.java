package com.loggerproject.coreservice.service.data.view.manager.view.update;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateModel;
import com.loggerproject.coreservice.data.repository.ViewModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.view.manager.view.ViewModelUtilService;
import com.loggerproject.coreservice.service.data.view.manager.view.create.ViewModelCreateService;
import com.loggerproject.coreservice.service.data.view.manager.view.delete.ViewModelDeleteService;
import com.loggerproject.coreservice.service.data.view.manager.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.get.ViewTemplateModelGetService;
import com.loggerproject.microserviceglobalresource.server.service.update.GlobalServerUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViewModelUpdateService extends GlobalServerUpdateService<ViewModel> {

    @Autowired
    ViewModelRepositoryRestResource ViewModelRepositoryRestResource;

    @Autowired
    ViewModelGetService viewModelGetService;

    @Autowired
    ViewModelUtilService viewModelUtilService;

    @Autowired
    ViewTemplateModelGetService viewTemplateModelGetService;

    @Autowired
    public ViewModelUpdateService(ViewModelRepositoryRestResource repository,
                                  @Lazy ViewModelCreateService globalServerCreateService,
                                  @Lazy ViewModelDeleteService globalServerDeleteService,
                                  @Lazy ViewModelGetService globalServerGetService,
                                  @Lazy ViewModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public ViewModel updateDataSchemaJSON(String id, String json) throws Exception {
        ViewModel model = viewModelGetService.validateAndFindOne(id);
        model.setDataSchemaJSON(json);
        viewModelUtilService.scrubAndValidateDataSchemaJSON(model);
        return update(model);
    }

    public ViewModel updateDefaultViewTemplateID(String id, String viewTemplateID) throws Exception {
        ViewModel view = viewModelGetService.validateAndFindOne(id);
        ViewTemplateModel viewTemplate = viewTemplateModelGetService.validateAndFindOne(viewTemplateID);
        if (!viewTemplate.getViewID().equals(id)) {
            throw new Exception("ViewTemplate.viewID does not match View.id");
        }
        view.setDefaultViewTemplateID(viewTemplateID);
        return update(view);
    }
}
