package com.loggerproject.coreservice.service.data.view.manager;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.document.viewtemplate.ViewTemplateModel;
import com.loggerproject.coreservice.service.data.view.manager.model.CreateViewTemplateViewRequest;
import com.loggerproject.coreservice.service.data.view.manager.model.CreateViewTemplateViewResponse;
import com.loggerproject.coreservice.service.data.view.view.ViewModelUtilService;
import com.loggerproject.coreservice.service.data.view.view.create.ViewModelCreateService;
import com.loggerproject.coreservice.service.data.view.view.update.ViewModelUpdateService;
import com.loggerproject.coreservice.service.data.view.viewtemplate.ViewTemplateModelUtilService;
import com.loggerproject.coreservice.service.data.view.viewtemplate.create.ViewTemplateModelCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class ViewManagerService {

    @Autowired
    ViewModelUtilService viewModelUtilService;

    @Autowired
    ViewTemplateModelUtilService viewTemplateModelUtilService;

    @Autowired
    ViewModelCreateService viewModelCreateService;

    @Autowired
    ViewModelUpdateService viewModelUpdateService;

    @Autowired
    ViewTemplateModelCreateService viewTemplateModelCreateService;

    public CreateViewTemplateViewResponse createViewTemplateViewRequest(CreateViewTemplateViewRequest request) throws Exception {
        scrubAndValidate(request);

        ViewModel view = new ViewModel();
        view.setDataSchemaJSON(request.getJsonSchema());
        view.setOtherViewTemplateIDs(new HashSet<>());
        view = viewModelCreateService.save(view);

        ViewTemplateModel viewTemplate = new ViewTemplateModel();
        viewTemplate.setHtml(request.getHtml());
        viewTemplate.setJs(request.getJs());
        viewTemplate.setCss(request.getCss());
        viewTemplate.setViewID(view.getID());
        viewTemplate = viewTemplateModelCreateService.save(viewTemplate);

        view.setDefaultViewTemplateID(viewTemplate.getID());
        view.getOtherViewTemplateIDs().add(viewTemplate.getID());
        view = viewModelUpdateService.update(view);

        CreateViewTemplateViewResponse response = new CreateViewTemplateViewResponse();
        response.setView(view);
        response.setViewTemplate(viewTemplate);
        return response;
    }

    public void scrubAndValidate(CreateViewTemplateViewRequest request) throws Exception {
        request.setJsonSchema(viewModelUtilService.scrubAndValidateDataSchemaJSON(request.getJsonSchema()));
        request.setHtml(viewTemplateModelUtilService.scrubAndValidateHTML(request.getHtml()));
        request.setJs(viewTemplateModelUtilService.scrubAndValidateJS(request.getJs()));
        request.setCss(viewTemplateModelUtilService.scrubAndValidateCSS(request.getCss()));
    }
}
