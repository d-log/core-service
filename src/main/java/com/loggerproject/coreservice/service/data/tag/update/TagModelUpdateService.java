package com.loggerproject.coreservice.service.data.tag.update;

import com.loggerproject.coreservice.data.document.tag.TagModel;
import com.loggerproject.coreservice.data.repository.TagModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.tag.create.TagModelCreateService;
import com.loggerproject.coreservice.service.data.tag.delete.TagModelDeleteService;
import com.loggerproject.coreservice.service.data.tag.get.TagModelGetService;
import com.loggerproject.microserviceglobalresource.server.service.update.GlobalServerUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TagModelUpdateService extends GlobalServerUpdateService<TagModel> {

    @Autowired
    TagModelRepositoryRestResource tagModelRepositoryRestResource;

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    public TagModelUpdateService(TagModelRepositoryRestResource repository,
                                 @Lazy TagModelCreateService globalServerCreateService,
                                 @Lazy TagModelDeleteService globalServerDeleteService,
                                 @Lazy TagModelGetService globalServerGetService,
                                 @Lazy TagModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public TagModel changeName(String id, String name) throws Exception {
        TagModel model = tagModelGetService.validateAndFindOne(id);
        model.setName(name);
        return this.update(model);
    }
}
