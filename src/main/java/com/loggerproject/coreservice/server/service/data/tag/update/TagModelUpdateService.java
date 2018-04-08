package com.loggerproject.coreservice.server.service.data.tag.update;

import com.loggerproject.coreservice.global.server.service.update.GlobalServerUpdateService;
import com.loggerproject.coreservice.server.data.document.tag.TagModel;
import com.loggerproject.coreservice.server.data.repository.TagModelRepository;
import com.loggerproject.coreservice.server.service.data.tag.create.TagModelCreateService;
import com.loggerproject.coreservice.server.service.data.tag.delete.TagModelDeleteService;
import com.loggerproject.coreservice.server.service.data.tag.get.TagModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TagModelUpdateService extends GlobalServerUpdateService<TagModel> {

    @Autowired
    TagModelRepository tagModelRepository;

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    public TagModelUpdateService(TagModelRepository repository,
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
