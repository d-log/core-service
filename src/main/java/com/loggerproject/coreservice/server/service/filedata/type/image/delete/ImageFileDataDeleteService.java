package com.loggerproject.coreservice.server.service.filedata.type.image.delete;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.image.ImageFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.AFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.image.create.ImageFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.image.get.ImageFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.image.update.ImageFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImageFileDataDeleteService extends AFileDataDeleteService<ImageFileData> {

    @Autowired
    public ImageFileDataDeleteService(@Lazy ImageFileDataCreateService globalServerCreateService,
                                      @Lazy ImageFileDataDeleteService globalServerDeleteService,
                                      @Lazy ImageFileDataGetService globalServerGetService,
                                      @Lazy ImageFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeDeleteValidate(FileModel model) {
        return model;
    }
}
