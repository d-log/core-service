package com.loggerproject.coreservice.service.file.type.impl.image.get;

import com.loggerproject.coreservice.data.document.file.extra.data.image.ImageFileData;
import com.loggerproject.coreservice.service.file.type.afiledata.get.AFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.image.create.ImageFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.image.delete.ImageFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.image.update.ImageFileModelUpdateService;
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
