package com.loggerproject.coreservice.service.data.view.view.create;

import com.loggerproject.coreservice.data.document.view.Template;
import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.repository.ViewModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.view.view.TemplateUtilService;
import com.loggerproject.coreservice.service.data.view.view.ViewModelUtilService;
import com.loggerproject.coreservice.service.data.view.view.delete.ViewModelDeleteService;
import com.loggerproject.coreservice.service.data.view.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.data.view.view.update.ViewModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.create.GlobalServerCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ViewModelCreateService extends GlobalServerCreateService<ViewModel> {

    @Autowired
    ViewModelRepositoryRestResource viewModelRepositoryRestResource;

    @Autowired
    ViewModelUtilService viewModelUtilService;

    @Autowired
    TemplateUtilService templateUtilService;

    @Autowired
    public ViewModelCreateService(ViewModelRepositoryRestResource repository,
                                  @Lazy ViewModelCreateService globalServerCreateService,
                                  @Lazy ViewModelDeleteService globalServerDeleteService,
                                  @Lazy ViewModelGetService globalServerGetService,
                                  @Lazy ViewModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public ViewModel scrubAndValidate(ViewModel model) throws Exception {
        Assert.notNull(model.getDefaultTemplateName(), "ViewModel.defaultTemplateName cannot be empty");
        Assert.notEmpty(model.getTemplates(), "ViewModel.templates must not be empty");
        Assert.notNull(model.getTemplates().get(model.getDefaultTemplateName()), "ViewModel.defaultTemplateName: '" + model.getDefaultTemplateName() + "' does not exist in ViewModel.templates");
        Assert.notNull(model.getDataSchemaJSON(), "ViewModel.dataSchemaJSON must not be empty");

        for (String name : model.getTemplates().keySet()) {
            validateTemplateName(name);
            Template template = model.getTemplates().get(name);
            templateUtilService.scrubAndValidateTemplate(template);
        }

        model.setDataSchemaJSON((viewModelUtilService.scrubAndValidateDataSchemaJSON(model.getDataSchemaJSON())));

        return model;
    }

    public void validateTemplateName(String templateName) throws Exception {
        if (!isAlpha(templateName)) {
            throw new Exception("ViewModel.templates name:'" + templateName + "' should only contain alpha characters");
        }
    }

    private Boolean isAlpha(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected ViewModel beforeSave(ViewModel model) throws Exception {
        model = scrubAndValidate(model);
        return super.beforeSave(model);
    }

}
