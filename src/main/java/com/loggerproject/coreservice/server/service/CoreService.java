package com.loggerproject.coreservice.server.service;

import com.loggerproject.coreservice.server.controller.api.model.LogDetailModel;
import com.loggerproject.coreservice.server.service.minions.LogDetailModelService;
import com.loggerproject.logservice.client.service.LogClientService;
import com.loggerproject.logservice.server.data.model.LogModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoreService {

    @Autowired
    LogClientService logClientService;

    @Autowired
    LogDetailModelService logDetailModelService;

    public LogDetailModel getLogDetailModel(String logID) throws Exception {
        LogModel logModel = logClientService.findOne(logID);

        if (logModel != null) {
            return logDetailModelService.buildLogDetailModel(logModel);
        }
        else {
            throw new Exception("404 non-existent " + LogModel.class + " ID: \'" + logID + "\'");
        }
    }
}



