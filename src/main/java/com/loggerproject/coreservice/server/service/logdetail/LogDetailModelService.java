package com.loggerproject.coreservice.server.service.logdetail;

import com.loggerproject.coreservice.server.model.log.detail.LogDetailModel;
import com.loggerproject.logservice.client.service.LogClientService;
import com.loggerproject.logservice.server.data.model.LogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class LogDetailModelService {
    @Autowired
    LogClientService logClientService;

    @Autowired
    LogDetailModelFreshService freshService;

    @Autowired
    LogDetailModelCachedService cacheService;

    public LogDetailModel findOne(String logID) throws Exception {
        LogModel logModel = logClientService.validateAndFindOne(logID);
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
