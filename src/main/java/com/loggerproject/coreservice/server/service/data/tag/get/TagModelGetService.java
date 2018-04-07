package com.loggerproject.coreservice.server.service.data.tag.get;

import com.loggerproject.coreservice.server.data.document.tag.TagModel;
import com.loggerproject.coreservice.server.data.repository.TagModelRepository;
import com.loggerproject.coreservice.server.service.data.tag.create.TagModelCreateService;
import com.loggerproject.coreservice.server.service.data.tag.delete.TagModelDeleteService;
import com.loggerproject.coreservice.server.service.data.tag.update.TagModelUpdateService;
import com.loggerproject.coreservice.global.server.service.get.GlobalServerGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagModelGetService extends GlobalServerGetService<TagModel> {

    @Autowired
    TagModelRepository tagModelRepository;

    @Autowired
    public TagModelGetService(TagModelRepository repository,
                              @Lazy TagModelCreateService globalServerCreateService,
                              @Lazy TagModelDeleteService globalServerDeleteService,
                              @Lazy TagModelGetService globalServerGetService,
                              @Lazy TagModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public List<TagModel> findByName(String name) {
        return this.tagModelRepository.findByName(name);
    }
}
