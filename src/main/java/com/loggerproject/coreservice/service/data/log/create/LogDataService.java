package com.loggerproject.coreservice.service.data.log.create;

import com.loggerproject.coreservice.data.document.log.model.logdata.ALogData;
import com.loggerproject.coreservice.data.document.log.model.logdata.LogDataScrubberValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;


@Service
public class LogDataService {

    @Autowired
    public List<LogDataScrubberValidator> logDataScrubberValidators;

    private HashMap<Class, LogDataScrubberValidator> map = new HashMap<>();

    @PostConstruct
    public void postConstruct() {
        for (LogDataScrubberValidator logDataScrubberValidator : logDataScrubberValidators) {
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
        LogDataScrubberValidator validator = map.get(logData.getClass());
        if (validator != null) {
            validator.scrubAndValidateLogData(logData);
        } else {

        }
    }
}