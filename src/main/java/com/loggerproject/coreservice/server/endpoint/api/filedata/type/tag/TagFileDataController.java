package com.loggerproject.coreservice.server.endpoint.api.filedata.type.tag;

import com.loggerproject.coreservice.server.endpoint.api.filedata.a.AFileDataController;
import com.loggerproject.coreservice.server.service.filedata.type.tag.create.TagFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.delete.TagFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.get.TagFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.update.TagFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file-data/tag")
public class TagFileDataController extends AFileDataController {

    @Autowired
    public TagFileDataController(TagFileDataCreateService globalServerCreateService,
                               TagFileDataDeleteService globalServerDeleteService,
                               TagFileDataGetService globalServerGetService,
                               TagFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}