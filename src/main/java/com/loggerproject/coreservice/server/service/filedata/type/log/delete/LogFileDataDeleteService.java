package com.loggerproject.coreservice.server.service.filedata.type.log.delete;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.AFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.model.ModelBoundedToLogException;
import com.loggerproject.coreservice.server.service.filedata.type.log.create.LogFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.LogFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.log.update.LogFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LogFileDataDeleteService extends AFileDataDeleteService<LogFileData> {

    @Autowired
    public LogFileDataDeleteService(@Lazy LogFileDataCreateService globalServerCreateService,
                                    @Lazy LogFileDataDeleteService globalServerDeleteService,
                                    @Lazy LogFileDataGetService globalServerGetService,
                                    @Lazy LogFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeDeleteValidate(FileModel model) throws Exception {
        return model;
    }
}
