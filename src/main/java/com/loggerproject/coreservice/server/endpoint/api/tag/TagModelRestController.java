package com.loggerproject.coreservice.server.endpoint.api.tag;

import com.loggerproject.coreservice.global.server.endpoint.api.GlobalModelController;
import com.loggerproject.coreservice.server.data.document.tag.TagModel;
import com.loggerproject.coreservice.server.service.data.tag.create.TagModelCreateService;
import com.loggerproject.coreservice.server.service.data.tag.delete.TagModelDeleteService;
import com.loggerproject.coreservice.server.service.data.tag.get.TagModelGetService;
import com.loggerproject.coreservice.server.service.data.tag.update.TagModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tag")
public class TagModelRestController extends GlobalModelController<TagModel> {

    @Autowired
    public TagModelRestController(TagModelCreateService globalServerCreateService,
                                  TagModelDeleteService globalServerDeleteService,
                                  TagModelGetService globalServerGetService,
                                  TagModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}