package com.loggerproject.coreservice.server.service.filedata.type.logdirectory.update;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.update.AFileDataUpdateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.create.LogDirectoryFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.delete.LogDirectoryFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileDataGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LogDirectoryFileDataUpdateService extends AFileDataUpdateService<LogDirectoryFileData> {

    @Autowired
    public LogDirectoryFileDataUpdateService(@Lazy LogDirectoryFileDataCreateService globalServerCreateService,
                                    @Lazy LogDirectoryFileDataDeleteService globalServerDeleteService,
                                    @Lazy LogDirectoryFileDataGetService globalServerGetService,
                                    @Lazy LogDirectoryFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public FileModel changeName(String id, String name) throws Exception {
        FileModel model = globalServerGetService.validateAndFindOne(id);
        model.getMetadata().setName(name);
        return this.update(model);
    }
}
