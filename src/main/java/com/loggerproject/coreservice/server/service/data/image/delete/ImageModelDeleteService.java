package com.loggerproject.coreservice.server.service.data.image.delete;

import com.loggerproject.coreservice.global.server.service.delete.GlobalServerDeleteService;
import com.loggerproject.coreservice.server.data.document.image.ImageModel;
import com.loggerproject.coreservice.server.data.repository.ImageModelRepository;
import com.loggerproject.coreservice.server.service.data.image.create.ImageModelCreateService;
import com.loggerproject.coreservice.server.service.data.image.get.ImageModelGetService;
import com.loggerproject.coreservice.server.service.data.image.update.ImageModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImageModelDeleteService extends GlobalServerDeleteService<ImageModel> {

    @Autowired
    ImageModelRepository ImageModelRepository;

    @Autowired
    public ImageModelDeleteService(ImageModelRepository repository,
                                   @Lazy ImageModelCreateService globalServerCreateService,
                                   @Lazy ImageModelDeleteService globalServerDeleteService,
                                   @Lazy ImageModelGetService globalServerGetService,
                                   @Lazy ImageModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
