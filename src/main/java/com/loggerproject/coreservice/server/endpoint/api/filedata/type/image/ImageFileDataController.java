package com.loggerproject.coreservice.server.endpoint.api.filedata.type.image;

import com.loggerproject.coreservice.server.endpoint.api.filedata.a.AFileDataController;
import com.loggerproject.coreservice.server.service.filedata.type.image.create.ImageFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.image.delete.ImageFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.image.get.ImageFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.image.update.ImageFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file-data/image")
public class ImageFileDataController extends AFileDataController {

    @Autowired
    public ImageFileDataController(ImageFileDataCreateService globalServerCreateService,
                                   ImageFileDataDeleteService globalServerDeleteService,
                                   ImageFileDataGetService globalServerGetService,
                                   ImageFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}