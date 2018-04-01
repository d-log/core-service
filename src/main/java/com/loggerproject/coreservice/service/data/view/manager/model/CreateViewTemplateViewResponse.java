package com.loggerproject.coreservice.service.data.view.manager.model;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateModel;
import lombok.Data;

@Data
public class CreateViewTemplateViewResponse {
    ViewModel view;
    ViewTemplateModel viewTemplate;
}
