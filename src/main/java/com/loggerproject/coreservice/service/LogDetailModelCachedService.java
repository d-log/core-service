package com.loggerproject.coreservice.service;

import com.loggerproject.coreservice.data.log.model.LogModel;
import com.loggerproject.coreservice.service.model.LogDetailModel;
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
