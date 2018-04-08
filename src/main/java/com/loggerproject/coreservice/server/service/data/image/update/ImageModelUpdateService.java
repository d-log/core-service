package com.loggerproject.coreservice.server.service.data.image.update;

import com.loggerproject.coreservice.global.server.service.update.GlobalServerUpdateService;
import com.loggerproject.coreservice.server.data.document.image.ImageModel;
import com.loggerproject.coreservice.server.data.repository.ImageModelRepository;
import com.loggerproject.coreservice.server.service.data.image.create.ImageModelCreateService;
import com.loggerproject.coreservice.server.service.data.image.delete.ImageModelDeleteService;
import com.loggerproject.coreservice.server.service.data.image.get.ImageModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImageModelUpdateService extends GlobalServerUpdateService<ImageModel> {

    @Autowired
    ImageModelRepository ImageModelRepository;

    @Autowired
    ImageModelGetService imageModelGetService;

    @Autowired
    public ImageModelUpdateService(ImageModelRepository repository,
                                   @Lazy ImageModelCreateService globalServerCreateService,
                                   @Lazy ImageModelDeleteService globalServerDeleteService,
                                   @Lazy ImageModelGetService globalServerGetService,
                                   @Lazy ImageModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
