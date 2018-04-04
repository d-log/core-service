package com.loggerproject.coreservice.service.data.image.create;

import com.loggerproject.coreservice.data.document.image.ImageModel;
import com.loggerproject.coreservice.data.repository.ImageModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.image.delete.ImageModelDeleteService;
import com.loggerproject.coreservice.service.data.image.get.ImageModelGetService;
import com.loggerproject.coreservice.service.data.image.update.ImageModelUpdateService;
import com.loggerproject.coreservice.service.data.log.get.LogModelGetService;
import com.loggerproject.microserviceglobalresource.server.service.create.GlobalServerCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImageModelCreateService extends GlobalServerCreateService<ImageModel> {

    @Autowired
    ImageModelRepositoryRestResource ImageModelRepositoryRestResource;

    @Autowired
    LogModelGetService logModelGetService;

    @Autowired
    public ImageModelCreateService(ImageModelRepositoryRestResource repository,
                                   @Lazy ImageModelCreateService globalServerCreateService,
                                   @Lazy ImageModelDeleteService globalServerDeleteService,
                                   @Lazy ImageModelGetService globalServerGetService,
                                   @Lazy ImageModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
