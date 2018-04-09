package com.loggerproject.coreservice.server.service.data.customlogdata.update;

import com.loggerproject.coreservice.global.server.service.update.GlobalServerUpdateService;
import com.loggerproject.coreservice.server.data.document.customlogdata.CustomLogDataModel;
import com.loggerproject.coreservice.server.data.document.customlogdata.extra.ValidateDataStatement;
import com.loggerproject.coreservice.server.data.repository.CustomLogDataModelRepository;
import com.loggerproject.coreservice.server.service.data.customlogdata.CustomLogDataModelUtilService;
import com.loggerproject.coreservice.server.service.data.customlogdata.create.CustomLogDataModelCreateService;
import com.loggerproject.coreservice.server.service.data.customlogdata.delete.CustomLogDataModelDeleteService;
import com.loggerproject.coreservice.server.service.data.customlogdata.get.CustomLogDataModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class CustomLogDataModelUpdateService extends GlobalServerUpdateService<CustomLogDataModel> {

    @Autowired
    CustomLogDataModelRepository CustomLogDataModelRepository;

    @Autowired
    CustomLogDataModelGetService customLogDataModelGetService;

    @Autowired
    CustomLogDataModelUtilService customLogDataModelUtilService;

    @Autowired
    public CustomLogDataModelUpdateService(CustomLogDataModelRepository repository,
                                           @Lazy CustomLogDataModelCreateService globalServerCreateService,
                                           @Lazy CustomLogDataModelDeleteService globalServerDeleteService,
                                           @Lazy CustomLogDataModelGetService globalServerGetService,
                                           @Lazy CustomLogDataModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public CustomLogDataModel updateLogDataType(String id, String logDataType) throws Exception {
        CustomLogDataModel model = customLogDataModelGetService.validateAndFindOne(id);
        customLogDataModelUtilService.validateLogDataTypeName(logDataType);
        model.setLogDataType(logDataType);
        return update(model);
    }

    public CustomLogDataModel updateDataSchemaJSON(String id, String json) throws Exception {
        CustomLogDataModel model = customLogDataModelGetService.validateAndFindOne(id);
        String dataSchemaJSON = customLogDataModelUtilService.scrubAndValidateDataSchemaJSON(json);
        model.setDataSchemaJSON(dataSchemaJSON);
        return update(model);
    }

    public CustomLogDataModel updateValidateDataStatement(String id, ValidateDataStatement validateDataStatement) throws Exception {
        CustomLogDataModel model = customLogDataModelGetService.validateAndFindOne(id);
        model.setValidateDataStatement(validateDataStatement);
        return update(model);
    }
}
