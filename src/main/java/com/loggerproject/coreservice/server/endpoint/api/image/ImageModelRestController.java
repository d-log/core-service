package com.loggerproject.coreservice.server.endpoint.api.image;

import com.loggerproject.coreservice.global.server.endpoint.api.GlobalModelController;
import com.loggerproject.coreservice.server.data.document.image.ImageModel;
import com.loggerproject.coreservice.server.service.data.image.create.ImageModelCreateService;
import com.loggerproject.coreservice.server.service.data.image.delete.ImageModelDeleteService;
import com.loggerproject.coreservice.server.service.data.image.get.ImageModelGetService;
import com.loggerproject.coreservice.server.service.data.image.update.ImageModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/image")
public class ImageModelRestController extends GlobalModelController<ImageModel> {

    @Autowired
    public ImageModelRestController(ImageModelCreateService globalServerCreateService,
                                    ImageModelDeleteService globalServerDeleteService,
                                    ImageModelGetService globalServerGetService,
                                    ImageModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
