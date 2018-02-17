package com.loggerproject.coreservice.server.service.minions.logdetail;

import com.loggerproject.coreservice.server.controller.api.model.LogDetailModel;
import com.loggerproject.logservice.client.service.LogClientService;
import com.loggerproject.logservice.server.data.model.LogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogDetailModelService {
    @Autowired
    LogClientService logClientService;

    @Autowired
    LogDetailModelFreshService freshService;

    @Autowired
    LogDetailModelCachedService cacheService;

    public LogDetailModel getLogDetailModel(String logID) throws Exception {
        LogModel logModel = logClientService.findOne(logID);

        if (logModel != null) {
            return this.getLogDetailModel(logModel);
        }
        else {
            throw new Exception("404 non-existent " + LogModel.class + " ID: \'" + logID + "\'");
        }
    }

    public LogDetailModel getLogDetailModel(LogModel logModel) {
        LogDetailModel logDetailModel = cacheService.getLogDetailModel(logModel);

        if (logDetailModel == null) {
            logDetailModel = freshService.getLogDetailModel(logModel);
            cacheService.cacheLogDetailModel(logDetailModel);
        }

        return logDetailModel;
    }
}
