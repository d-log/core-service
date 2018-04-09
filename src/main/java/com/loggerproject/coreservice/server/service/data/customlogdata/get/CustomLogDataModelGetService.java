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

import java.util.ArrayList;
import java.util.Collection;
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
        CustomLogDataModel model = findByLogDataType(logDataType);
        if (model == null) {
            throw new ModelNotFoundException("non-existent " + genericName + " with logDataType: '" + logDataType + "'");
        }
        return model;
    }

    public List<CustomLogDataModel> findByLogDataTypes(Collection<String> logDataTypes) {
        List<CustomLogDataModel> models = new ArrayList<>();
        for (String logDataType : logDataTypes) {
            models.add(findByLogDataType(logDataType));
        }
        return models;
    }

    public CustomLogDataModel findByLogDataType(String logDataType) {
        CustomLogDataModel model = null;
        List<CustomLogDataModel> models = customLogDataModelRepository.findByLogDataType(logDataType);
        if (models.size() != 0) {
            model = models.get(0);
        }
        return model;
    }
}
