package com.loggerproject.coreservice.server.service.filedata.type.tag.get;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.get.AFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.create.TagFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.delete.TagFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.update.TagFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagFileDataGetService extends AFileDataGetService<TagFileData> {

    @Autowired
    public TagFileDataGetService(@Lazy TagFileDataCreateService globalServerCreateService,
                                 @Lazy TagFileDataDeleteService globalServerDeleteService,
                                 @Lazy TagFileDataGetService globalServerGetService,
                                 @Lazy TagFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public List<FileModel> findByName(String name) {
        return repository.findByMetadata_typeAndMetadata_name(genericClass.getSimpleName(), name);
    }
}
