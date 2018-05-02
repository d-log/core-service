package com.loggerproject.coreservice.service.file.type.impl.image.create;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.image.ImageFileData;
import com.loggerproject.coreservice.service.file.type.afiledata.create.AFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.image.delete.ImageFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.image.get.ImageFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.image.update.ImageFileModelUpdateService;
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
