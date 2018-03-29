package com.loggerproject.coreservice.service.log.create;

import com.loggerproject.coreservice.data.document.log.SchemaDataSource;
import com.loggerproject.coreservice.data.document.log.ViewData;
import com.loggerproject.coreservice.service.view.ViewModelUtilService;
import com.loggerproject.coreservice.service.viewtemplate.get.ViewTemplateModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchemaDataSourceService {

    @Autowired
    ViewModelUtilService viewModelUtilService;

    @Autowired
    ViewTemplateModelGetService viewTemplateModelService;

    public void scrubAndValidate(ViewData viewData) throws Exception {
        SchemaDataSource source = viewData.getSchemaDataSource();

        // validate both view id and the json data against view's json schema
        viewModelUtilService.validateJsonData(source.getViewID(), viewData.getData());

        if (source.getViewTemplateID() != null) {
            viewTemplateModelService.validateId(source.getViewTemplateID());
        }
    }
}
