package com.loggerproject.coreservice.server.service.filedata.type.log.get;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.server.data.repository.FileModelRepository;
import com.loggerproject.coreservice.server.service.filedata.afiledata.get.AFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.log.create.LogFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.log.delete.LogFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.log.update.LogFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogFileDataGetService extends AFileDataGetService<LogFileData> {

    @Autowired
    FileModelRepository fileModelRepository;

    @Autowired
    public LogFileDataGetService(@Lazy LogFileDataCreateService globalServerCreateService,
                                 @Lazy LogFileDataDeleteService globalServerDeleteService,
                                 @Lazy LogFileDataGetService globalServerGetService,
                                 @Lazy LogFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public List<FileModel> findByName(String name) {
        return fileModelRepository.findByMetadata_typeAndMetadata_name(genericClass.getSimpleName(), name);
    }
}
