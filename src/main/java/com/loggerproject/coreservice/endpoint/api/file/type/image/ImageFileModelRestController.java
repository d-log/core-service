package com.loggerproject.coreservice.endpoint.api.file.type.image;

import com.loggerproject.coreservice.endpoint.api.file.type.AFileModelRestController;
import com.loggerproject.coreservice.service.file.type.impl.image.create.ImageFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.image.delete.ImageFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.image.get.ImageFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.image.update.ImageFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file-data/image")
public class ImageFileModelRestController extends AFileModelRestController {

    @Autowired
    public ImageFileModelRestController(ImageFileModelCreateService globalServerCreateService,
                                        ImageFileModelDeleteService globalServerDeleteService,
                                        ImageFileModelGetService globalServerGetService,
                                        ImageFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}