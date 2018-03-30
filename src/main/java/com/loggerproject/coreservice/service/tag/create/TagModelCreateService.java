package com.loggerproject.coreservice.service.tag.create;

import com.loggerproject.coreservice.data.document.tag.TagModel;
import com.loggerproject.coreservice.data.repository.TagModelRepositoryRestResource;
import com.loggerproject.coreservice.service.log.get.LogModelGetService;
import com.loggerproject.coreservice.service.tag.delete.TagModelDeleteService;
import com.loggerproject.coreservice.service.tag.get.TagModelGetService;
import com.loggerproject.coreservice.service.tag.update.TagModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.create.GlobalServerCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class TagModelCreateService extends GlobalServerCreateService<TagModel> {

    @Autowired
    TagModelRepositoryRestResource tagModelRepositoryRestResource;

    @Autowired
    LogModelGetService logModelGetService;

    @Autowired
    public TagModelCreateService(TagModelRepositoryRestResource repository,
                                 @Lazy TagModelDeleteService globalServerDeleteService,
                                 @Lazy TagModelGetService globalServerGetService,
                                 @Lazy TagModelUpdateService globalServerUpdateService) {
        super(repository, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public void scrubAndValidate(TagModel model) throws Exception {
        model.setLogIDs(model.getLogIDs() != null ? model.getLogIDs() : new HashSet<>());
        model.setName(model.getName() == null ? "" : model.getName());

        logModelGetService.validateIds(model.getLogIDs());
    }

    @Override
    protected void beforeSave(TagModel model) throws Exception {
        scrubAndValidate(model);
        super.beforeSave(model);
    }
}
