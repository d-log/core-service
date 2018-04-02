package com.loggerproject.coreservice.service.data.view.view.delete;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.repository.ViewModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.view.view.create.ViewModelCreateService;
import com.loggerproject.coreservice.service.data.view.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.data.view.view.update.ViewModelUpdateService;
import com.loggerproject.coreservice.service.data.view.viewtemplate.delete.ViewTemplateModelDeleteService;
import com.loggerproject.microserviceglobalresource.server.service.delete.GlobalServerDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViewModelDeleteService extends GlobalServerDeleteService<ViewModel> {

    @Autowired
    ViewModelRepositoryRestResource ViewModelRepositoryRestResource;

    @Autowired
    ViewTemplateModelDeleteService viewTemplateModelDeleteService;

    @Autowired
    public ViewModelDeleteService(ViewModelRepositoryRestResource repository,
                                  @Lazy ViewModelCreateService globalServerCreateService,
                                  @Lazy ViewModelDeleteService globalServerDeleteService,
                                  @Lazy ViewModelGetService globalServerGetService,
                                  @Lazy ViewModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    protected ViewModel updateOtherDocuments(ViewModel model) throws Exception {
        for (String viewTemplateID : model.getOtherViewTemplateIDs()) {
            viewTemplateModelDeleteService.delete(viewTemplateID);
        }
        return model;
    }

    @Override
    protected ViewModel afterDelete(ViewModel model) throws Exception {
        model = updateOtherDocuments(model);
        return super.afterDelete(model);
    }
}
