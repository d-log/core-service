package com.loggerproject.coreservice.server.service.data.tag.create;

import com.loggerproject.coreservice.global.server.service.create.GlobalServerCreateService;
import com.loggerproject.coreservice.server.data.document.tag.TagModel;
import com.loggerproject.coreservice.server.data.repository.TagModelRepository;
import com.loggerproject.coreservice.server.service.data.log.get.regular.LogModelGetService;
import com.loggerproject.coreservice.server.service.data.tag.delete.TagModelDeleteService;
import com.loggerproject.coreservice.server.service.data.tag.get.TagModelGetService;
import com.loggerproject.coreservice.server.service.data.tag.update.TagModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;

@Service
public class TagModelCreateService extends GlobalServerCreateService<TagModel> {

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    LogModelGetService logModelGetService;

    @Autowired
    public TagModelCreateService(TagModelRepository repository,
                                 @Lazy TagModelCreateService globalServerCreateService,
                                 @Lazy TagModelDeleteService globalServerDeleteService,
                                 @Lazy TagModelGetService globalServerGetService,
                                 @Lazy TagModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public TagModel beforeSaveScrubAndValidate(TagModel model) throws Exception {
        Assert.hasText(model.getName(), "TagModel.name must not be empty");
        Assert.isTrue(CollectionUtils.isEmpty(model.getLogIDs()), "TagModel.logIDs must be empty");

        if (tagModelGetService.findByName(model.getName()).size() > 0) {
            throw new Exception("TagModel.name: '" + model.getName() + "' already exists");
        }

        model.setLogIDs(new HashSet<>());

        return model;
    }
}
