package com.loggerproject.coreservice.server.service.viewmanager;

import com.loggerproject.coreservice.server.model.view.manager.CreateViewModel;
import com.loggerproject.viewservice.client.service.ViewClientService;
import com.loggerproject.viewservice.server.data.model.ViewModel;
import com.loggerproject.viewtemplateservice.client.service.ViewTemplateClientService;
import com.loggerproject.viewtemplateservice.server.data.model.ViewTemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewManagerService {

    @Autowired
    CreateViewModelService createViewModelService;

    @Autowired
    ViewClientService viewClientService;

    @Autowired
    ViewTemplateClientService viewTemplateClientService;

    public CreateViewModel createViewModel(CreateViewModel createViewModel) throws Exception {
        return createViewModelService.createViewModel(createViewModel);
    }

    public ViewTemplateModel createViewTemplateModel(ViewTemplateModel viewTemplateModel) throws Exception {
        ViewModel viewModel = viewClientService.validateAndFindOne(viewTemplateModel.getViewID());

        // TODO set ViewTemplateName here or within the ViewTemplateService server?
        ViewTemplateModel createdViewTemplateModel = viewTemplateClientService.save(viewTemplateModel);
        viewModel.getOtherViewTemplateIDs().add(createdViewTemplateModel.getID());

        return createdViewTemplateModel;
    }
}
