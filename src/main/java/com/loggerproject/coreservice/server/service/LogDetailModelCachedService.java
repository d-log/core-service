package com.loggerproject.coreservice.server.service;

import com.loggerproject.coreservice.server.model.LogDetailModel;
import com.loggerproject.logservice.server.data.model.LogModel;
import org.springframework.stereotype.Service;

@Service
public class LogDetailModelCachedService {

    public LogDetailModel getLogDetailModel(LogModel logModel) {
        LogDetailModel logDetailModel = null;

        //TODO implement (from MongoDB or Memcached?)

        return logDetailModel;
    }

    public void cacheLogDetailModel(LogDetailModel logDetailModel) {

    }
}
