package com.loggerproject.coreservice.server.data.document.log.extra.logdata;

import com.loggerproject.coreservice.server.service.data.customlogdata.CustomLogDataModelUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

@Service
public class LogDataScrubberValidatorService {

    @Autowired
    CustomLogDataModelUtilService customLogDataModelUtilService;

    // simple class name to validator objects
    private HashMap<String, ALogDataScrubberValidator> map2Validator = new HashMap<>();

    @Autowired
    public void LogDataScrubberValidatorService(List<ALogDataScrubberValidator> logDataScrubberValidators) {
        for (ALogDataScrubberValidator logDataScrubberValidator : logDataScrubberValidators) {
            String name = GenericTypeResolver.resolveTypeArgument(logDataScrubberValidator.getClass(), ALogDataScrubberValidator.class).getSimpleName();
            map2Validator.put(name, logDataScrubberValidator);
        }
    }

    public void scrubAndValidate(List<LogData> logDatas) throws Exception {
        for (LogData logData : logDatas) {
            scrubAndValidate(logData);
        }
    }

    @SuppressWarnings(value= "unchecked")
    public void scrubAndValidate(LogData logData) throws Exception {
        String logDataType = logData.getLogDataType();
        String data = logData.getData();

        Assert.isTrue(!StringUtils.isEmpty(logDataType), "LogData.logDataType cannot be empty");
        Assert.isTrue(!StringUtils.isEmpty(data), "LogData.data cannot be empty");

        ALogDataScrubberValidator validator = map2Validator.get(logDataType);
        if (validator != null) {
            data = validator.scrubAndValidateLogDataString(data);
            logData.setData(data);
        } else {
            customLogDataModelUtilService.validateDataByLogDataType(data, logDataType);
        }
    }
}