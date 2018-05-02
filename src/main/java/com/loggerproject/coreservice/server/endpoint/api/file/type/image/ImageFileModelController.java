package com.loggerproject.coreservice.server.endpoint.api.file.type.image;

import com.loggerproject.coreservice.server.endpoint.api.file.a.AFileModelController;
import com.loggerproject.coreservice.server.service.filedata.type.image.create.ImageFileModelCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.image.delete.ImageFileModelDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.image.get.ImageFileModelGetService;
import com.loggerproject.coreservice.server.service.filedata.type.image.update.ImageFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file-data/image")
public class ImageFileModelController extends AFileModelController {

    @Autowired
    public ImageFileModelController(ImageFileModelCreateService globalServerCreateService,
                                    ImageFileModelDeleteService globalServerDeleteService,
                                    ImageFileModelGetService globalServerGetService,
                                    ImageFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}