package com.loggerproject.coreservice.server.service.viewmanager;

import com.loggerproject.coreservice.server.model.view.manager.CreateViewModel;
import com.loggerproject.viewservice.client.service.ViewClientService;
import com.loggerproject.viewservice.server.data.model.ViewModel;
import com.loggerproject.viewtemplateservice.client.service.ViewTemplateClientService;
import com.loggerproject.viewtemplateservice.server.data.model.ViewTemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CreateViewModelService {

    @Autowired
    ViewClientService viewClientService;

    @Autowired
    ViewTemplateClientService viewTemplateClientService;

    public CreateViewModel createViewModel(CreateViewModel createViewModel) throws Exception {
        this.scrubAndValidate(createViewModel);

        ViewModel viewModel = createViewModel.getViewModel();
        viewModel = viewClientService.save(viewModel);

        ViewTemplateModel defaultViewTemplateModel = createViewModel.getDefaultViewTemplateModel();
        defaultViewTemplateModel = this.saveViewTemplateModel(defaultViewTemplateModel, viewModel);

        List<ViewTemplateModel> createdOtherViewTemplateModels = new ArrayList<>();
        for (ViewTemplateModel otherViewTemplateModel : createViewModel.getOtherViewTemplateModels()) {
            ViewTemplateModel createdModel = this.saveViewTemplateModel(otherViewTemplateModel, viewModel);
            createdOtherViewTemplateModels.add(createdModel);
        }

        List<String> otherViewTemplateIDs = createdOtherViewTemplateModels.stream().map(ViewTemplateModel::getID).collect(Collectors.toList());
        viewModel.setDefaultViewTemplateID(defaultViewTemplateModel.getID());
        viewModel.setOtherViewTemplateIDs(otherViewTemplateIDs);
        viewModel = viewClientService.update(viewModel.getID(), viewModel);

        CreateViewModel createdViewModel = new CreateViewModel();
        createdViewModel.setViewModel(viewModel);
        createdViewModel.setDefaultViewTemplateModel(defaultViewTemplateModel);
        createdViewModel.setOtherViewTemplateModels(createdOtherViewTemplateModels);
        return createdViewModel;
    }

    private void scrubAndValidate(CreateViewModel createViewModel) throws Exception {
        Assert.notNull(createViewModel, "createViewModel cannot be null");
        Assert.notNull(createViewModel.getDefaultViewTemplateModel(), "defaultViewTemplateModel cannot be null");
        Assert.notNull(createViewModel.getViewModel(), "viewModel cannot be null");

        if (createViewModel.getOtherViewTemplateModels() == null) {
            createViewModel.setOtherViewTemplateModels(new ArrayList<>());
        }
    }

    private ViewTemplateModel saveViewTemplateModel(ViewTemplateModel viewTemplateModel, ViewModel viewModel) throws Exception {
        viewTemplateModel.setViewID(viewModel.getID());
        return viewTemplateClientService.save(viewTemplateModel);
    }
}
