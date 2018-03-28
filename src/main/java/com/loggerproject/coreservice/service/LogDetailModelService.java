package com.loggerproject.coreservice.service;

import com.loggerproject.coreservice.data.log.LogModelService;
import com.loggerproject.coreservice.data.log.model.LogModel;
import com.loggerproject.coreservice.service.model.LogDetailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogDetailModelService {
    @Autowired
    LogModelService logModelService;

    @Autowired
    LogDetailModelFreshService freshService;

    @Autowired
    LogDetailModelCachedService cacheService;

    public LogDetailModel findOne(String logID) throws Exception {
        LogModel logModel = logModelService.validateAndFindOne(logID);
        return this.findOne(logModel);
    }

    public LogDetailModel findOne(LogModel logModel) {
        LogDetailModel logDetailModel = cacheService.getLogDetailModel(logModel);

        if (logDetailModel == null) {
            logDetailModel = freshService.getLogDetailModel(logModel);
            cacheService.cacheLogDetailModel(logDetailModel);
        }

        return logDetailModel;
    }
}
