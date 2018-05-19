package com.loggerproject.coreservice.service.image.get;

import com.loggerproject.coreservice.data.model.image.ImageModel;
import com.loggerproject.coreservice.data.repository.ImageModelRepository;
import com.loggerproject.coreservice.service.aglobal.get.AGlobalModelGetService;
import com.loggerproject.coreservice.service.image.create.ImageModelCreateService;
import com.loggerproject.coreservice.service.image.delete.ImageModelDeleteService;
import com.loggerproject.coreservice.service.image.update.ImageModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ImageModelGetService extends AGlobalModelGetService<ImageModel> {

    @Autowired
    public ImageModelGetService(ImageModelRepository repository,
                                @Lazy ImageModelCreateService globalServerCreateService,
                                @Lazy ImageModelDeleteService globalServerDeleteService,
                                @Lazy ImageModelGetService globalServerGetService,
                                @Lazy ImageModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
