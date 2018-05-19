package com.loggerproject.coreservice.service.image.create;

import com.loggerproject.coreservice.data.model.image.ImageModel;
import com.loggerproject.coreservice.data.repository.ImageModelRepository;
import com.loggerproject.coreservice.service.aglobal.create.AGlobalModelCreateService;
import com.loggerproject.coreservice.service.image.delete.ImageModelDeleteService;
import com.loggerproject.coreservice.service.image.get.ImageModelGetService;
import com.loggerproject.coreservice.service.image.update.ImageModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImageModelCreateService extends AGlobalModelCreateService<ImageModel> {

    @Autowired
    public ImageModelCreateService(ImageModelRepository repository,
                                   @Lazy ImageModelCreateService globalServerCreateService,
                                   @Lazy ImageModelDeleteService globalServerDeleteService,
                                   @Lazy ImageModelGetService globalServerGetService,
                                   @Lazy ImageModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
