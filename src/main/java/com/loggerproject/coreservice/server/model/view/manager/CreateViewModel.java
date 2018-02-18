package com.loggerproject.coreservice.server.model.view.manager;

import com.loggerproject.viewservice.server.data.model.ViewModel;
import com.loggerproject.viewtemplateservice.server.data.model.ViewTemplateModel;
import lombok.Data;

import java.util.List;

@Data
public class CreateViewModel {
    ViewModel viewModel;
    ViewTemplateModel defaultViewTemplateModel;
    List<ViewTemplateModel> otherViewTemplateModels;
}
