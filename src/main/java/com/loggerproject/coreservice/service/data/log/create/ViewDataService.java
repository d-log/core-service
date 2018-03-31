package com.loggerproject.coreservice.service.data.log.create;

import com.loggerproject.coreservice.data.document.log.SchemaDataSource;
import com.loggerproject.coreservice.data.document.log.ViewData;
import com.loggerproject.coreservice.service.data.view.manager.view.ViewModelUtilService;
import com.loggerproject.coreservice.service.data.view.manager.viewtemplate.get.ViewTemplateModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ViewDataService {

    @Autowired
    ViewModelUtilService viewModelUtilService;

    @Autowired
    ViewTemplateModelGetService viewTemplateModelService;

    public void scrubAndValidate(List<ViewData> viewDatas) throws Exception {
        for (ViewData viewData : viewDatas) {
            scrubAndValidate(viewData);
        }
    }

    public void scrubAndValidate(ViewData viewData) throws Exception {
        Assert.notNull(viewData.getSchemaDataSource(), "SchemaDataSource cannot be null");

        SchemaDataSource source = viewData.getSchemaDataSource();

        if (source.getViewTemplateID() != null) {
            viewTemplateModelService.validateId(source.getViewTemplateID());
        }

        // validate both view id and the json data against view's json schema
        viewModelUtilService.validateJsonDataAgainstSchema(source.getViewID(), viewData.getData());
    }
}
