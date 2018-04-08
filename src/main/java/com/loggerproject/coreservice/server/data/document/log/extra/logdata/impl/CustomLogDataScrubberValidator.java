package com.loggerproject.coreservice.server.data.document.log.extra.logdata.impl;

import com.google.gson.Gson;
import com.loggerproject.coreservice.server.data.document.log.extra.logdata.ALogDataScrubberValidator;
import com.loggerproject.coreservice.server.service.data.customlogdata.CustomLogDataModelUtilService;
import com.loggerproject.coreservice.server.service.data.customlogdata.get.CustomLogDataModelGetService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CustomLogDataScrubberValidator extends ALogDataScrubberValidator<CustomLogData> {

    @Autowired
    CustomLogDataModelGetService customLogDataModelGetService;

    @Autowired
    CustomLogDataModelUtilService customLogDataModelUtilService;

    @Override
    public void scrubAndValidateLogData(CustomLogData customLogData) throws Exception {
        Assert.isTrue(StringUtils.isNotBlank(customLogData.getLogDataType()), "LogData.logDataType cannot be empty");
        String jsonData = new Gson().toJson(customLogData.getProperties());
        customLogDataModelUtilService.validateDataByLogDataType(jsonData, customLogData.getLogDataType());
    }
}
