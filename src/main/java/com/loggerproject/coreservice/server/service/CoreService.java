package com.loggerproject.coreservice.server.service;

import com.loggerproject.coreservice.server.controller.api.model.LogDetailModel;
import com.loggerproject.coreservice.server.service.minions.logdetail.LogDetailModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CoreService {

    @Autowired
    LogDetailModelService logDetailModelService;

    public LogDetailModel getLogDetailModel(String logID) throws Exception {
        return logDetailModelService.getLogDetailModel(logID);
    }
}



