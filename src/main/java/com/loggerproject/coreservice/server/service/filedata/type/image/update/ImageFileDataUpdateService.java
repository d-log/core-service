package com.loggerproject.coreservice.server.service.filedata.type.image.update;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.image.ImageFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.update.AFileDataUpdateService;
import com.loggerproject.coreservice.server.service.filedata.type.image.create.ImageFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.image.delete.ImageFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.image.get.ImageFileDataGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImageFileDataUpdateService extends AFileDataUpdateService<ImageFileData> {

    @Autowired
    public ImageFileDataUpdateService(@Lazy ImageFileDataCreateService globalServerCreateService,
                                    @Lazy ImageFileDataDeleteService globalServerDeleteService,
                                    @Lazy ImageFileDataGetService globalServerGetService,
                                    @Lazy ImageFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeUpdateScrubAndValidate(FileModel model) {
        return model;
    }
}
