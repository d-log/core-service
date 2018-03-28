package com.loggerproject.coreservice.data.log.service;

import com.loggerproject.coreservice.data.log.model.SchemaDataSource;
import com.loggerproject.coreservice.data.log.model.ViewData;
import com.loggerproject.coreservice.data.view.service.ViewModelService;
import com.loggerproject.coreservice.data.viewtemplate.service.ViewTemplateModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchemaDataSourceService {

    @Autowired
    ViewModelService viewModelService;

    @Autowired
    ViewTemplateModelService viewTemplateModelService;

    public ViewData scrubAndValidate(ViewData viewData) throws Exception {
        SchemaDataSource source = viewData.getSchemaDataSource();

        // validate both view id and the json data against view's json schema
        viewModelService.validateJsonData(source.getViewID(), viewData.getData());

        if (source.getViewTemplateID() != null) {
            viewTemplateModelService.validateId(source.getViewTemplateID());
        }

        return viewData;
    }
}
