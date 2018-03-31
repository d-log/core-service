package com.loggerproject.coreservice.service.data.tag.get;

import com.loggerproject.coreservice.data.document.tag.TagModel;
import com.loggerproject.coreservice.data.repository.TagModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.tag.create.TagModelCreateService;
import com.loggerproject.coreservice.service.data.tag.delete.TagModelDeleteService;
import com.loggerproject.coreservice.service.data.tag.update.TagModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.get.GlobalServerGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagModelGetService extends GlobalServerGetService<TagModel> {

    @Autowired
    TagModelRepositoryRestResource tagModelRepositoryRestResource;

    @Autowired
    public TagModelGetService(TagModelRepositoryRestResource repository,
                              @Lazy TagModelCreateService globalServerCreateService,
                              @Lazy TagModelDeleteService globalServerDeleteService,
                              @Lazy TagModelGetService globalServerGetService,
                              @Lazy TagModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public List<TagModel> findByName(String name) {
        return this.tagModelRepositoryRestResource.findByName(name);
    }
}
