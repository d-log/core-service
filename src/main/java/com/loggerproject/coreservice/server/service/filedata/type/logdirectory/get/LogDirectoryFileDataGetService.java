package com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.server.data.repository.FileModelRepository;
import com.loggerproject.coreservice.server.service.filedata.afiledata.get.AFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.create.LogDirectoryFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.delete.LogDirectoryFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.update.LogDirectoryFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogDirectoryFileDataGetService extends AFileDataGetService<LogDirectoryFileData> {

    @Autowired
    FileModelRepository fileModelRepository;

    @Autowired
    public LogDirectoryFileDataGetService(@Lazy LogDirectoryFileDataCreateService globalServerCreateService,
                                 @Lazy LogDirectoryFileDataDeleteService globalServerDeleteService,
                                 @Lazy LogDirectoryFileDataGetService globalServerGetService,
                                 @Lazy LogDirectoryFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public List<FileModel> findByName(String name) {
        return fileModelRepository.findByMetadata_typeAndMetadata_name(genericClass.getSimpleName(), name);
    }
}
