package com.loggerproject.coreservice.server.service.filedata.type.logdirectory.delete;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.AFileModelDeleteService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.model.ModelBoundedToLogException;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.create.LogDirectoryFileModelCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileModelGetService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.update.LogDirectoryFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LogDirectoryFileModelDeleteService extends AFileModelDeleteService<LogDirectoryFileData> {

    @Autowired
    public LogDirectoryFileModelDeleteService(@Lazy LogDirectoryFileModelCreateService globalServerCreateService,
                                              @Lazy LogDirectoryFileModelDeleteService globalServerDeleteService,
                                              @Lazy LogDirectoryFileModelGetService globalServerGetService,
                                              @Lazy LogDirectoryFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeDeleteValidate(FileModel model) throws Exception {
        LogDirectoryFileData directory = (LogDirectoryFileData) model.getData();
        if (directory.getLogFileIDs().size() > 0) {
            throw new ModelBoundedToLogException(model.getId(), directory.getLogFileIDs());
        }
        return model;
    }
}
