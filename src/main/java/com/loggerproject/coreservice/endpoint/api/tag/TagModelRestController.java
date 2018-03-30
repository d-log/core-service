package com.loggerproject.coreservice.endpoint.api.tag;

import com.loggerproject.coreservice.data.document.tag.TagModel;
import com.loggerproject.coreservice.service.tag.create.TagModelCreateService;
import com.loggerproject.coreservice.service.tag.delete.TagModelDeleteService;
import com.loggerproject.coreservice.service.tag.get.TagModelGetService;
import com.loggerproject.coreservice.service.tag.update.TagModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.endpoint.api.GlobalModelController;
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