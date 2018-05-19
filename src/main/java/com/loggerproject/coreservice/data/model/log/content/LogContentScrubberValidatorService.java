package com.loggerproject.coreservice.data.model.log.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;

@Service
public class LogContentScrubberValidatorService {

    // simple class name to validator objects
    // example: "VideoYouTubeLogData" -> VideoYouTubeLogDataScrubberValidator object
    private HashMap<String, ALogContentScrubberValidator> map2Validator = new HashMap<>();

    @Autowired
    public void LogDataScrubberValidatorService(List<ALogContentScrubberValidator> logDataScrubberValidators) {
        for (ALogContentScrubberValidator logDataScrubberValidator : logDataScrubberValidators) {
            String name = GenericTypeResolver.resolveTypeArgument(logDataScrubberValidator.getClass(), ALogContentScrubberValidator.class).getSimpleName();
            map2Validator.put(name, logDataScrubberValidator);
        }
    }

    public void scrubAndValidate(List<LogContent> logDatas) throws Exception {
        for (LogContent logData : logDatas) {
            scrubAndValidate(logData);
        }
    }

    @SuppressWarnings(value = "unchecked")
    public void scrubAndValidate(LogContent logData) throws Exception {
        String logDataType = logData.getLogContentType();
        Assert.hasText(logDataType, "FileModel.data.logContents.logContentType cannot be empty");
        ALogContentScrubberValidator validator = map2Validator.get(logDataType);

        if (validator != null) {
            Object data = logData.getData();
            data = validator.scrubAndValidateLogDataObject(data);
            logData.setData(data);
        } else {
            throw new Exception("CustomLogDataUtilService not implemented");
//            customLogDataModelUtilService.validateDataByLogDataType(filedata, logContentType);
        }

        if (logData.getCss() != null) {
            // TODO validate css
        }
    }
}