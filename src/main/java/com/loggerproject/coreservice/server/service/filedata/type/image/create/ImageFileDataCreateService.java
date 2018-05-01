package com.loggerproject.coreservice.server.service.filedata.type.image.create;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.image.ImageFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.create.AFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.image.delete.ImageFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.image.get.ImageFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.image.update.ImageFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImageFileDataCreateService extends AFileDataCreateService<ImageFileData> {

    @Autowired
    public ImageFileDataCreateService(@Lazy ImageFileDataCreateService globalServerCreateService,
                                      @Lazy ImageFileDataDeleteService globalServerDeleteService,
                                      @Lazy ImageFileDataGetService globalServerGetService,
                                      @Lazy ImageFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeSaveScrubAndValidate(FileModel model) {
        return model;
    }
}
