package com.loggerproject.coreservice.service.data.image.delete;

import com.loggerproject.coreservice.data.document.image.ImageModel;
import com.loggerproject.coreservice.data.repository.ImageModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.image.create.ImageModelCreateService;
import com.loggerproject.coreservice.service.data.image.get.ImageModelGetService;
import com.loggerproject.coreservice.service.data.image.update.ImageModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.delete.GlobalServerDeleteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImageModelDeleteService extends GlobalServerDeleteService<ImageModel> {

    @Autowired
    ImageModelRepositoryRestResource ImageModelRepositoryRestResource;

    @Autowired
    public ImageModelDeleteService(ImageModelRepositoryRestResource repository,
                                   @Lazy ImageModelCreateService globalServerCreateService,
                                   @Lazy ImageModelDeleteService globalServerDeleteService,
                                   @Lazy ImageModelGetService globalServerGetService,
                                   @Lazy ImageModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
