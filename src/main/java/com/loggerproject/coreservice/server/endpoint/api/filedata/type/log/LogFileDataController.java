package com.loggerproject.coreservice.server.endpoint.api.filedata.type.log;

import com.loggerproject.coreservice.server.endpoint.api.filedata.a.AFileDataController;
import com.loggerproject.coreservice.server.service.filedata.type.log.create.LogFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.log.delete.LogFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.log.update.LogFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file-data/log")
public class LogFileDataController extends AFileDataController {

    @Autowired
    public LogFileDataController(LogFileDataCreateService globalServerCreateService,
                                 LogFileDataDeleteService globalServerDeleteService,
                                 LogFileDataGetService globalServerGetService,
                                 LogFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}