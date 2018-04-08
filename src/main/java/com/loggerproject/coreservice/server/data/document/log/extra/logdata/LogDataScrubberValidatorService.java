package com.loggerproject.coreservice.server.data.document.log.extra.logdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;


@Service
public class LogDataScrubberValidatorService {

    @Autowired
    public List<ALogDataScrubberValidator> logDataScrubberValidators;

    private HashMap<Class, ALogDataScrubberValidator> map = new HashMap<>();

    @PostConstruct
    public void postConstruct() {
        for (ALogDataScrubberValidator logDataScrubberValidator : logDataScrubberValidators) {
            map.put(logDataScrubberValidator.getGenericClass(), logDataScrubberValidator);
        }
    }

    public void scrubAndValidate(List<ALogData> logDatas) throws Exception {
        for (ALogData logData : logDatas) {
            scrubAndValidate(logData);
        }
    }

    @SuppressWarnings(value= "unchecked")
    public void scrubAndValidate(ALogData logData) throws Exception {
        ALogDataScrubberValidator validator = map.get(logData.getClass());
        if (validator != null) {
            validator.scrubAndValidateLogData(logData);
        } else {
            throw new Exception("Missing LogDataScrubberValidator implementation for LogData class: '" + logData.getClass().getName() + "'");
        }
    }
}