package com.loggerproject.coreservice.server.service.filedata.type.log.update;

import com.loggerproject.coreservice.server.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.update.AFileDataUpdateService;
import com.loggerproject.coreservice.server.service.filedata.type.log.create.LogFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.log.delete.LogFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogFileDataGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LogFileDataUpdateService extends AFileDataUpdateService<LogFileData> {

    @Autowired
    LogFileDataGetService tagFileDataGetService;

    @Autowired
    public LogFileDataUpdateService(@Lazy LogFileDataCreateService globalServerCreateService,
                                    @Lazy LogFileDataDeleteService globalServerDeleteService,
                                    @Lazy LogFileDataGetService globalServerGetService,
                                    @Lazy LogFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
