package com.loggerproject.coreservice.service.data.image.update;

import com.loggerproject.coreservice.data.document.image.ImageModel;
import com.loggerproject.coreservice.data.repository.ImageModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.image.create.ImageModelCreateService;
import com.loggerproject.coreservice.service.data.image.delete.ImageModelDeleteService;
import com.loggerproject.coreservice.service.data.image.get.ImageModelGetService;
import com.loggerproject.microserviceglobalresource.server.service.update.GlobalServerUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImageModelUpdateService extends GlobalServerUpdateService<ImageModel> {

    @Autowired
    ImageModelRepositoryRestResource ImageModelRepositoryRestResource;

    @Autowired
    ImageModelGetService imageModelGetService;

    @Autowired
    public ImageModelUpdateService(ImageModelRepositoryRestResource repository,
                                   @Lazy ImageModelCreateService globalServerCreateService,
                                   @Lazy ImageModelDeleteService globalServerDeleteService,
                                   @Lazy ImageModelGetService globalServerGetService,
                                   @Lazy ImageModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
