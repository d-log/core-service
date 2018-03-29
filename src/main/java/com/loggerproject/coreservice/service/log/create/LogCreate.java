package com.loggerproject.coreservice.service.log.create;

import com.loggerproject.coreservice.data.document.log.LogModel;
import com.loggerproject.coreservice.data.document.log.ViewData;
import com.loggerproject.coreservice.service.log.LogModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogCreate {

    @Autowired
    ViewDataService viewDataService;

    @Autowired
    LogModelService logModelService;

    public void create(LogModel model) throws Exception {

        for (ViewData viewData : model.getViewDatas()) {
            viewDataService.scrubAndValidate(viewData);
        }

        this.logModelService.save(model);
    }
}
