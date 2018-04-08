package com.loggerproject.coreservice.server.service.data.customlogdata.get;

import com.loggerproject.coreservice.global.server.service.get.GlobalServerGetService;
import com.loggerproject.coreservice.global.server.service.get.model.ModelNotFoundException;
import com.loggerproject.coreservice.server.data.document.customlogdata.CustomLogDataModel;
import com.loggerproject.coreservice.server.data.repository.CustomLogDataModelRepository;
import com.loggerproject.coreservice.server.service.data.customlogdata.create.CustomLogDataModelCreateService;
import com.loggerproject.coreservice.server.service.data.customlogdata.delete.CustomLogDataModelDeleteService;
import com.loggerproject.coreservice.server.service.data.customlogdata.update.CustomLogDataModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomLogDataModelGetService extends GlobalServerGetService<CustomLogDataModel> {

    @Autowired
    CustomLogDataModelRepository customLogDataModelRepository;

    @Autowired
    public CustomLogDataModelGetService(CustomLogDataModelRepository repository,
                                        @Lazy CustomLogDataModelCreateService globalServerCreateService,
                                        @Lazy CustomLogDataModelDeleteService globalServerDeleteService,
                                        @Lazy CustomLogDataModelGetService globalServerGetService,
                                        @Lazy CustomLogDataModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public CustomLogDataModel validateAndFindByLogDataType(String logDataType) throws Exception {
        List<CustomLogDataModel> models = customLogDataModelRepository.findByLogDataType(logDataType);
        if (models.size() == 0) {
            throw new ModelNotFoundException("non-existent " + genericName + " with logDataType: '" + logDataType + "'");
        } else {
            return models.get(0);
        }
    }
}
