package com.loggerproject.coreservice.service.data.view.manager.view.create;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.repository.ViewModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.view.manager.view.ViewModelUtilService;
import com.loggerproject.coreservice.service.data.view.manager.view.delete.ViewModelDeleteService;
import com.loggerproject.coreservice.service.data.view.manager.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.data.view.manager.view.update.ViewModelUpdateService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.get.ViewTemplateModelGetService;
import com.loggerproject.microserviceglobalresource.server.service.create.GlobalServerCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class ViewModelCreateService extends GlobalServerCreateService<ViewModel> {

    @Autowired
    ViewModelRepositoryRestResource viewModelRepositoryRestResource;

    @Autowired
    ViewTemplateModelGetService viewTemplateModelGetService;

    @Autowired
    ViewModelUtilService viewModelUtilService;

    @Autowired
    public ViewModelCreateService(ViewModelRepositoryRestResource repository,
                                  @Lazy ViewModelCreateService globalServerCreateService,
                                  @Lazy ViewModelDeleteService globalServerDeleteService,
                                  @Lazy ViewModelGetService globalServerGetService,
                                  @Lazy ViewModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public void scrubAndValidate(ViewModel model) throws Exception {
        viewTemplateModelGetService.validateId(model.getDefaultViewTemplateID());

        model.setOtherViewTemplateIDs(model.getOtherViewTemplateIDs() != null ? model.getOtherViewTemplateIDs() : new HashSet<>());
        model.setDataSchemaJSON(model.getDataSchemaJSON() != null ? model.getDataSchemaJSON() : "");

        viewTemplateModelGetService.validateIds(model.getOtherViewTemplateIDs());

        viewModelUtilService.scrubAndValidateDataSchemaJSON(model);
    }

    @Override
    protected void beforeSave(ViewModel model) throws Exception {
        scrubAndValidate(model);
        super.beforeSave(model);
    }

    @Override
    public ViewModel save(ViewModel model) throws Exception {
        throw new Exception("Use ViewManagerService to save ViewModels");
    }
}
