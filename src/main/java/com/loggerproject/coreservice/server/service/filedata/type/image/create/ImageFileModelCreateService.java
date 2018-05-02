package com.loggerproject.coreservice.server.service.filedata.type.image.create;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.image.ImageFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.create.AFileModelCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.image.delete.ImageFileModelDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.image.get.ImageFileModelGetService;
import com.loggerproject.coreservice.server.service.filedata.type.image.update.ImageFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImageFileModelCreateService extends AFileModelCreateService<ImageFileData> {

    @Autowired
    public ImageFileModelCreateService(@Lazy ImageFileModelCreateService globalServerCreateService,
                                       @Lazy ImageFileModelDeleteService globalServerDeleteService,
                                       @Lazy ImageFileModelGetService globalServerGetService,
                                       @Lazy ImageFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeSaveScrubAndValidate(FileModel model) {
        return model;
    }
}
