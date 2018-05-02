package com.loggerproject.coreservice.service.file.type.impl.image.delete;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.image.ImageFileData;
import com.loggerproject.coreservice.service.file.type.afiledata.delete.AFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.image.create.ImageFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.image.get.ImageFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.image.update.ImageFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImageFileModelDeleteService extends AFileModelDeleteService<ImageFileData> {

    @Autowired
    public ImageFileModelDeleteService(@Lazy ImageFileModelCreateService globalServerCreateService,
                                       @Lazy ImageFileModelDeleteService globalServerDeleteService,
                                       @Lazy ImageFileModelGetService globalServerGetService,
                                       @Lazy ImageFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeDeleteValidate(FileModel model) {
        return model;
    }
}
