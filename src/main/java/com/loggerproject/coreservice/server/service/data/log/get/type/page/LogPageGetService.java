package com.loggerproject.coreservice.server.service.data.log.get.type.page;

import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.service.data.log.get.type.ALogTypeGetService;
import org.springframework.stereotype.Service;

@Service
public class LogPageGetService extends ALogTypeGetService<LogPageModel> {
    @Override
    public LogPageModel getByLogModel(LogModel log) {
        return null;
    }
}
