package com.loggerproject.coreservice.server.service.filedata.type.image.update;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.image.ImageFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.update.AFileModelUpdateService;
import com.loggerproject.coreservice.server.service.filedata.type.image.create.ImageFileModelCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.image.delete.ImageFileModelDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.image.get.ImageFileModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImageFileModelUpdateService extends AFileModelUpdateService<ImageFileData> {

    @Autowired
    public ImageFileModelUpdateService(@Lazy ImageFileModelCreateService globalServerCreateService,
                                       @Lazy ImageFileModelDeleteService globalServerDeleteService,
                                       @Lazy ImageFileModelGetService globalServerGetService,
                                       @Lazy ImageFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeUpdateScrubAndValidate(FileModel model) {
        return model;
    }
}
