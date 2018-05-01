package com.loggerproject.coreservice.server.service.filedata.type.logdirectory.create;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.create.AFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.delete.LogDirectoryFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.update.LogDirectoryFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LogDirectoryFileDataCreateService extends AFileDataCreateService<LogDirectoryFileData> {

    @Autowired
    LogDirectoryFileDataGetService tagModelGetService;

    @Autowired
    public LogDirectoryFileDataCreateService(@Lazy LogDirectoryFileDataCreateService globalServerCreateService,
                                    @Lazy LogDirectoryFileDataDeleteService globalServerDeleteService,
                                    @Lazy LogDirectoryFileDataGetService globalServerGetService,
                                    @Lazy LogDirectoryFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeSaveScrubAndValidate(FileModel model) throws Exception {
        return model;
    }
}
