package com.loggerproject.coreservice.server.service.data.log.get;

import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.data.repository.LogModelRepository;
import com.loggerproject.coreservice.server.service.data.log.create.LogModelCreateService;
import com.loggerproject.coreservice.server.service.data.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.server.service.data.log.update.LogModelUpdateService;
import com.loggerproject.coreservice.global.server.service.get.GlobalServerGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LogModelGetService extends GlobalServerGetService<LogModel> {

    @Autowired
    LogModelRepository LogModelRepository;

    @Autowired
    public LogModelGetService(LogModelRepository repository,
                              @Lazy LogModelCreateService globalServerCreateService,
                              @Lazy LogModelDeleteService globalServerDeleteService,
                              @Lazy LogModelGetService globalServerGetService,
                              @Lazy LogModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
