package com.loggerproject.coreservice.server.service.filedata.type.image.get;

import com.loggerproject.coreservice.server.data.document.file.extra.data.image.ImageFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.get.AFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.image.create.ImageFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.image.delete.ImageFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.image.update.ImageFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImageFileDataGetService extends AFileDataGetService<ImageFileData> {

    @Autowired
    public ImageFileDataGetService(@Lazy ImageFileDataCreateService globalServerCreateService,
                                 @Lazy ImageFileDataDeleteService globalServerDeleteService,
                                 @Lazy ImageFileDataGetService globalServerGetService,
                                 @Lazy ImageFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
