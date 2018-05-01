package com.loggerproject.coreservice.server.service.filedata.type.logdirectory.delete;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.AFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.model.ModelBoundedToLogException;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.create.LogDirectoryFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.update.LogDirectoryFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LogDirectoryFileDataDeleteService extends AFileDataDeleteService<LogDirectoryFileData> {

    @Autowired
    public LogDirectoryFileDataDeleteService(@Lazy LogDirectoryFileDataCreateService globalServerCreateService,
                                    @Lazy LogDirectoryFileDataDeleteService globalServerDeleteService,
                                    @Lazy LogDirectoryFileDataGetService globalServerGetService,
                                    @Lazy LogDirectoryFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeDeleteValidate(FileModel model) throws Exception {
        return model;
    }
}
