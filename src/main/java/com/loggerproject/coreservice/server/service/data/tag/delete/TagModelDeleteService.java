package com.loggerproject.coreservice.server.service.data.tag.delete;

import com.loggerproject.coreservice.server.data.document.tag.TagModel;
import com.loggerproject.coreservice.server.data.repository.TagModelRepository;
import com.loggerproject.coreservice.server.service.data.tag.create.TagModelCreateService;
import com.loggerproject.coreservice.server.service.data.tag.get.TagModelGetService;
import com.loggerproject.coreservice.server.service.data.tag.update.TagModelUpdateService;
import com.loggerproject.coreservice.global.server.service.delete.GlobalServerDeleteService;
import com.loggerproject.coreservice.global.server.service.delete.model.ModelBoundedToLogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TagModelDeleteService extends GlobalServerDeleteService<TagModel> {

    @Autowired
    TagModelRepository tagModelRepository;

    @Autowired
    public TagModelDeleteService(TagModelRepository repository,
                                 @Lazy TagModelCreateService globalServerCreateService,
                                 @Lazy TagModelDeleteService globalServerDeleteService,
                                 @Lazy TagModelGetService globalServerGetService,
                                 @Lazy TagModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public TagModel validateDelete(TagModel model) throws Exception {
        if(model.getLogIDs().size() > 0) {
            throw new ModelBoundedToLogException(model.getID(), model.getLogIDs());
        }

        return model;
    }

    @Override
    public TagModel beforeDelete(TagModel model) throws Exception {
        model = validateDelete(model);
        return super.beforeDelete(model);
    }
}
