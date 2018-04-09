package com.loggerproject.coreservice.server.service.data.customlogdata.create;

import com.loggerproject.coreservice.global.server.service.create.GlobalServerCreateService;
import com.loggerproject.coreservice.server.data.document.customlogdata.CustomLogDataModel;
import com.loggerproject.coreservice.server.data.repository.CustomLogDataModelRepository;
import com.loggerproject.coreservice.server.service.data.customlogdata.CustomLogDataModelUtilService;
import com.loggerproject.coreservice.server.service.data.customlogdata.delete.CustomLogDataModelDeleteService;
import com.loggerproject.coreservice.server.service.data.customlogdata.get.CustomLogDataModelGetService;
import com.loggerproject.coreservice.server.service.data.customlogdata.update.CustomLogDataModelUpdateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CustomLogDataModelCreateService extends GlobalServerCreateService<CustomLogDataModel> {

    @Autowired
    CustomLogDataModelRepository customLogDataModelRepository;

    @Autowired
    CustomLogDataModelUtilService customLogDataModelUtilService;

    @Autowired
    public CustomLogDataModelCreateService(CustomLogDataModelRepository repository,
                                           @Lazy CustomLogDataModelCreateService globalServerCreateService,
                                           @Lazy CustomLogDataModelDeleteService globalServerDeleteService,
                                           @Lazy CustomLogDataModelGetService globalServerGetService,
                                           @Lazy CustomLogDataModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public CustomLogDataModel beforeSaveScrubAndValidate(CustomLogDataModel model) throws Exception {
        Assert.notNull(model.getDataSchemaJSON(), "CustomLogDataModel.dataSchemaJSON must not be empty");
        customLogDataModelUtilService.validateLogDataTypeName(model.getLogDataType());
        model.setDataSchemaJSON((customLogDataModelUtilService.scrubAndValidateDataSchemaJSON(model.getDataSchemaJSON())));
        return model;
    }
}
