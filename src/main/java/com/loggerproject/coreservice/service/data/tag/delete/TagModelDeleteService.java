package com.loggerproject.coreservice.service.data.tag.delete;

import com.loggerproject.coreservice.data.document.tag.TagModel;
import com.loggerproject.coreservice.data.repository.TagModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.tag.create.TagModelCreateService;
import com.loggerproject.coreservice.service.data.tag.get.TagModelGetService;
import com.loggerproject.coreservice.service.data.tag.update.TagModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.delete.GlobalServerDeleteService;
import com.loggerproject.microserviceglobalresource.server.service.delete.model.ModelBoundedToLogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TagModelDeleteService extends GlobalServerDeleteService<TagModel> {

    @Autowired
    TagModelRepositoryRestResource tagModelRepositoryRestResource;

    @Autowired
    public TagModelDeleteService(TagModelRepositoryRestResource repository,
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