package com.loggerproject.coreservice.endpoint.api.file.type.tag;

import com.loggerproject.coreservice.endpoint.api.file.type.AFileModelRestController;
import com.loggerproject.coreservice.service.file.type.impl.tag.create.TagFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.tag.delete.TagFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.tag.get.TagFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.tag.update.TagFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/file/tag")
public class TagFileModelRestController extends AFileModelRestController {

    @Autowired
    public TagFileModelRestController(TagFileModelCreateService globalServerCreateService,
                                      TagFileModelDeleteService globalServerDeleteService,
                                      TagFileModelGetService globalServerGetService,
                                      TagFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}