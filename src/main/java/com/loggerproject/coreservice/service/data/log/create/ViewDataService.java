package com.loggerproject.coreservice.service.data.log.create;

import com.loggerproject.coreservice.data.document.log.ViewData;
import com.loggerproject.coreservice.service.data.view.view.TemplateUtilService;
import com.loggerproject.coreservice.service.data.view.view.ViewModelUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class ViewDataService {

    @Autowired
    ViewModelUtilService viewModelUtilService;

    @Autowired
    TemplateUtilService templateUtilService;

    public void scrubAndValidate(List<ViewData> viewDatas) throws Exception {
        for (ViewData viewData : viewDatas) {
            scrubAndValidate(viewData);
        }
    }

    public void scrubAndValidate(ViewData viewData) throws Exception {
        Assert.notNull(viewData.getViewID(), "ViewData.viewID cannot be empty");
        if (viewData.getViewTemplateID() != null) {
            templateUtilService.validateId(viewData.getViewID(), viewData.getViewTemplateID());
        }

        // validate both view id and the json data against view's json schema
        viewModelUtilService.validateJsonData(viewData.getData(), viewData.getViewID());
    }
}
