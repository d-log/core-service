package com.loggerproject.coreservice.service.data.view.view.update;

import com.loggerproject.coreservice.data.document.view.Template;
import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.repository.ViewModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.view.view.TemplateUtilService;
import com.loggerproject.coreservice.service.data.view.view.ViewModelUtilService;
import com.loggerproject.coreservice.service.data.view.view.create.ViewModelCreateService;
import com.loggerproject.coreservice.service.data.view.view.delete.ViewModelDeleteService;
import com.loggerproject.coreservice.service.data.view.view.get.ViewModelGetService;
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
    TemplateUtilService templateUtilService;

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
        model.setDataSchemaJSON(viewModelUtilService.scrubAndValidateDataSchemaJSON(model.getDataSchemaJSON()));

        return update(model);
    }

    public ViewModel addTemplate(String viewID, String templateName, Template template) throws Exception {
        ViewModel view = viewModelGetService.validateAndFindOne(viewID);
        if(view.getTemplates().get(templateName) != null) {
            throw new Exception("ViewModel: '" + viewID + "' already contains template with name: '" + templateName + "' existing names: " + view.getTemplates().keySet().toString());
        }

        templateUtilService.scrubAndValidateTemplate(template);
        view.getTemplates().put(templateName, template);

        return update(view);
    }

    public ViewModel updateDefaultViewTemplateName(String viewID, String templateName) throws Exception {
        ViewModel view = viewModelGetService.validateAndFindOne(viewID);
        templateUtilService.validateId(viewID, templateName);
        view.setDefaultTemplateName(templateName);

        return update(view);
    }

    public ViewModel updateTemplate(String viewID, String templateName, Template template) throws Exception {
        ViewModel view = viewModelGetService.validateAndFindOne(viewID);

        Template oldTemplate = templateUtilService.validateAndFindOne(viewID, templateName);
        oldTemplate.setHtml(template.getHtml());
        oldTemplate.setJs(template.getJs());
        oldTemplate.setCss(template.getCss());
        templateUtilService.scrubAndValidateTemplate(oldTemplate);

        view.getTemplates().put(templateName, oldTemplate);

        return update(view);
    }
}
