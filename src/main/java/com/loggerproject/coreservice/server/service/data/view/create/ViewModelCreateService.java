package com.loggerproject.coreservice.server.service.data.view.create;

import com.loggerproject.coreservice.server.data.document.view.Template;
import com.loggerproject.coreservice.server.data.document.view.ViewModel;
import com.loggerproject.coreservice.server.data.repository.ViewModelRepository;
import com.loggerproject.coreservice.server.service.data.view.TemplateUtilService;
import com.loggerproject.coreservice.server.service.data.view.ViewModelUtilService;
import com.loggerproject.coreservice.server.service.data.view.delete.ViewModelDeleteService;
import com.loggerproject.coreservice.server.service.data.view.get.ViewModelGetService;
import com.loggerproject.coreservice.server.service.data.view.update.ViewModelUpdateService;
import com.loggerproject.coreservice.global.server.service.create.GlobalServerCreateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ViewModelCreateService extends GlobalServerCreateService<ViewModel> {

    @Autowired
    ViewModelRepository viewModelRepository;

    @Autowired
    ViewModelUtilService viewModelUtilService;

    @Autowired
    TemplateUtilService templateUtilService;

    @Autowired
    public ViewModelCreateService(ViewModelRepository repository,
                                  @Lazy ViewModelCreateService globalServerCreateService,
                                  @Lazy ViewModelDeleteService globalServerDeleteService,
                                  @Lazy ViewModelGetService globalServerGetService,
                                  @Lazy ViewModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public ViewModel beforeSaveScrubAndValidate(ViewModel model) throws Exception {
        Assert.notNull(model.getDefaultTemplateName(), "ViewModel.defaultTemplateName cannot be empty");
        Assert.notEmpty(model.getTemplates(), "ViewModel.templates must not be empty");
        Assert.notNull(model.getTemplates().get(model.getDefaultTemplateName()), "ViewModel.defaultTemplateName: '" + model.getDefaultTemplateName() + "' does not exist in ViewModel.templates");
        Assert.notNull(model.getDataSchemaJSON(), "ViewModel.dataSchemaJSON must not be empty");
        Assert.notNull(model.getName(), "ViewModel.name cannot be empty");

        if (!StringUtils.isAlpha(model.getName())) {
            throw new Exception("ViewModel.name: '" + model.getName() + "' should only contain alpha characters");
        }

        if (viewModelRepository.findByName(model.getName()).size() > 0) {
            throw new Exception("ViewModel.name: '" + model.getName() + "' already exists");
        }

        for (String name : model.getTemplates().keySet()) {
            if (!StringUtils.isAlpha(name)) {
                throw new Exception("ViewModel.templates name:'" + name + "' should only contain alpha characters");
            }
            Template template = model.getTemplates().get(name);
            templateUtilService.scrubAndValidateTemplate(template);
        }

        model.setDataSchemaJSON((viewModelUtilService.scrubAndValidateDataSchemaJSON(model.getDataSchemaJSON())));

        return model;
    }
}
