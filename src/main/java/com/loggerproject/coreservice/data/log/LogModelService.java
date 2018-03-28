package com.loggerproject.coreservice.data.log;

import com.loggerproject.coreservice.data.log.endpoint.LogModelRepositoryRestResource;
import com.loggerproject.coreservice.data.log.model.LogModel;
import com.loggerproject.coreservice.data.log.model.ViewData;
import com.loggerproject.coreservice.data.log.service.ViewDataService;
import com.loggerproject.microserviceglobalresource.server.service.GlobalServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LogModelService extends GlobalServerService<LogModel> {

    @Autowired
    ViewDataService viewDataService;

    @Autowired
    public LogModelService(LogModelRepositoryRestResource repository) {
        super(repository);
    }

    protected LogModel scrubAndValidate(LogModel model) throws Exception {
        model.setDirectoryIDs(model.getDirectoryIDs() != null ? model.getDirectoryIDs() : new ArrayList());
        model.setTagIDs(model.getTagIDs() != null ? model.getTagIDs() : new ArrayList());

        for (ViewData viewData : model.getViewDatas()) {
            viewDataService.scrubAndValidate(viewData);
        }

        return model;
    }
}
