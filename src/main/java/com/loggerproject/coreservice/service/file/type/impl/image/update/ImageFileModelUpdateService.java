package com.loggerproject.coreservice.service.file.type.impl.image.update;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.image.ImageFileData;
import com.loggerproject.coreservice.service.file.type.afiledata.update.AFileModelUpdateService;
import com.loggerproject.coreservice.service.file.type.impl.image.create.ImageFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.image.delete.ImageFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.image.get.ImageFileModelGetService;
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
