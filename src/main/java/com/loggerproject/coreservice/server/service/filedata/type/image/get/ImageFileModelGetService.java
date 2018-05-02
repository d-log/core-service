package com.loggerproject.coreservice.server.service.filedata.type.image.get;

import com.loggerproject.coreservice.server.data.document.file.extra.data.image.ImageFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.get.AFileModelGetService;
import com.loggerproject.coreservice.server.service.filedata.type.image.create.ImageFileModelCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.image.delete.ImageFileModelDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.image.update.ImageFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImageFileModelGetService extends AFileModelGetService<ImageFileData> {

    @Autowired
    public ImageFileModelGetService(@Lazy ImageFileModelCreateService globalServerCreateService,
                                    @Lazy ImageFileModelDeleteService globalServerDeleteService,
                                    @Lazy ImageFileModelGetService globalServerGetService,
                                    @Lazy ImageFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
