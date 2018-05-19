package com.loggerproject.coreservice.endpoint.api.image;

import com.loggerproject.coreservice.data.model.image.ImageModel;
import com.loggerproject.coreservice.endpoint.api.AGlobalModelRestController;
import com.loggerproject.coreservice.service.image.create.ImageModelCreateService;
import com.loggerproject.coreservice.service.image.delete.ImageModelDeleteService;
import com.loggerproject.coreservice.service.image.get.ImageModelGetService;
import com.loggerproject.coreservice.service.image.update.ImageModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/image")
public class ImageModelRestController extends AGlobalModelRestController<ImageModel> {

    @Autowired
    public ImageModelRestController(ImageModelCreateService globalServerCreateService,
                                    ImageModelDeleteService globalServerDeleteService,
                                    ImageModelGetService globalServerGetService,
                                    ImageModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}