package com.loggerproject.coreservice.server.service.data.customlogdata.delete;

import com.loggerproject.coreservice.global.server.service.delete.GlobalServerDeleteService;
import com.loggerproject.coreservice.server.data.document.customlogdata.CustomLogDataModel;
import com.loggerproject.coreservice.server.data.repository.CustomLogDataModelRepository;
import com.loggerproject.coreservice.server.service.data.customlogdata.create.CustomLogDataModelCreateService;
import com.loggerproject.coreservice.server.service.data.customlogdata.get.CustomLogDataModelGetService;
import com.loggerproject.coreservice.server.service.data.customlogdata.update.CustomLogDataModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CustomLogDataModelDeleteService extends GlobalServerDeleteService<CustomLogDataModel> {

    @Autowired
    CustomLogDataModelRepository CustomLogDataModelRepository;

    @Autowired
    public CustomLogDataModelDeleteService(CustomLogDataModelRepository repository,
                                           @Lazy CustomLogDataModelCreateService globalServerCreateService,
                                           @Lazy CustomLogDataModelDeleteService globalServerDeleteService,
                                           @Lazy CustomLogDataModelGetService globalServerGetService,
                                           @Lazy CustomLogDataModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
