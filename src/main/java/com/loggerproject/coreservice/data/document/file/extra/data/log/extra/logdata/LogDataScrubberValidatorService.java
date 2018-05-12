package com.loggerproject.coreservice.data.document.file.extra.data.log.extra.logdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;

@Service
public class LogDataScrubberValidatorService {

    // simple class name to validator objects
    // example: "VideoYouTubeLogData" -> VideoYouTubeLogDataScrubberValidator object
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

    @SuppressWarnings(value = "unchecked")
    public void scrubAndValidate(LogData logData) throws Exception {
        String logDataType = logData.getLogDataType();
        Assert.hasText(logDataType, "FileModel.data.logDatas.logDataType cannot be empty");
        ALogDataScrubberValidator validator = map2Validator.get(logDataType);

        if (validator != null) {
            Object data = logData.getData();
            data = validator.scrubAndValidateLogDataObject(data);
            logData.setData(data);
        } else {
            throw new Exception("CustomLogDataUtilService not implemented");
//            customLogDataModelUtilService.validateDataByLogDataType(filedata, logDataType);
        }

        if (logData.getCss() != null) {
            // TODO validate css
        }
    }
}