package com.loggerproject.coreservice.service.log.create;

import com.loggerproject.coreservice.data.model.log.SchemaDataSource;
import com.loggerproject.coreservice.data.model.log.ViewData;
import com.loggerproject.coreservice.service.view.ViewModelService;
import com.loggerproject.coreservice.service.viewtemplate.ViewTemplateModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchemaDataSourceService {

    @Autowired
    ViewModelService viewModelService;

    @Autowired
    ViewTemplateModelService viewTemplateModelService;

    public void scrubAndValidate(ViewData viewData) throws Exception {
        SchemaDataSource source = viewData.getSchemaDataSource();

        // validate both view id and the json data against view's json schema
        viewModelService.validateJsonData(source.getViewID(), viewData.getData());

        if (source.getViewTemplateID() != null) {
            viewTemplateModelService.validateId(source.getViewTemplateID());
        }
    }
}
