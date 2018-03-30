package com.loggerproject.coreservice.service.tag.update;

import com.loggerproject.coreservice.data.document.tag.TagModel;
import com.loggerproject.coreservice.data.repository.TagModelRepositoryRestResource;
import com.loggerproject.coreservice.service.tag.create.TagModelCreateService;
import com.loggerproject.coreservice.service.tag.delete.TagModelDeleteService;
import com.loggerproject.coreservice.service.tag.get.TagModelGetService;
import com.loggerproject.microserviceglobalresource.server.service.update.GlobalServerUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TagModelUpdateService extends GlobalServerUpdateService<TagModel> {

    @Autowired
    TagModelRepositoryRestResource tagModelRepositoryRestResource;

    @Autowired
    public TagModelUpdateService(TagModelRepositoryRestResource repository,
                                 @Lazy TagModelCreateService globalServerCreateService,
                                 @Lazy TagModelDeleteService globalServerDeleteService,
                                 @Lazy TagModelGetService globalServerGetService,
                                 @Lazy TagModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public void changeName(String id, String name) throws Exception {
        TagModel model = (TagModel) globalServerGetService.validateAndFindOne(id);
        model.setName(name);
        this.update(model.getID(), model);
    }
}
