package com.loggerproject.coreservice.server.endpoint.api.filedata.type.logdirectory;

import com.loggerproject.coreservice.server.endpoint.api.filedata.a.AFileDataController;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.create.LogDirectoryFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.delete.LogDirectoryFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.update.LogDirectoryFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file-data/log-directory")
public class LogDirectoryFileDataController extends AFileDataController {

    @Autowired
    public LogDirectoryFileDataController(LogDirectoryFileDataCreateService globalServerCreateService,
                                          LogDirectoryFileDataDeleteService globalServerDeleteService,
                                          LogDirectoryFileDataGetService globalServerGetService,
                                          LogDirectoryFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}