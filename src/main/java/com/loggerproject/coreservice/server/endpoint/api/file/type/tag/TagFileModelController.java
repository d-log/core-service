package com.loggerproject.coreservice.server.endpoint.api.file.type.tag;

import com.loggerproject.coreservice.server.endpoint.api.file.a.AFileModelController;
import com.loggerproject.coreservice.server.service.filedata.type.tag.create.TagFileModelCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.delete.TagFileModelDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.get.TagFileModelGetService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.update.TagFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file-data/tag")
public class TagFileModelController extends AFileModelController {

    @Autowired
    public TagFileModelController(TagFileModelCreateService globalServerCreateService,
                                  TagFileModelDeleteService globalServerDeleteService,
                                  TagFileModelGetService globalServerGetService,
                                  TagFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}