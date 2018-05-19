package com.loggerproject.coreservice.service.tag.get;

import com.loggerproject.coreservice.data.model.tag.TagModel;
import com.loggerproject.coreservice.data.repository.TagModelRepository;
import com.loggerproject.coreservice.service.aglobal.get.AGlobalModelGetService;
import com.loggerproject.coreservice.service.tag.create.TagModelCreateService;
import com.loggerproject.coreservice.service.tag.delete.TagModelDeleteService;
import com.loggerproject.coreservice.service.tag.update.TagModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TagModelGetService extends AGlobalModelGetService<TagModel> {

    @Autowired
    public TagModelGetService(TagModelRepository repository,
                              @Lazy TagModelCreateService globalServerCreateService,
                              @Lazy TagModelDeleteService globalServerDeleteService,
                              @Lazy TagModelGetService globalServerGetService,
                              @Lazy TagModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
