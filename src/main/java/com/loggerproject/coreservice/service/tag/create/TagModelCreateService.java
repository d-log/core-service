package com.loggerproject.coreservice.service.tag.create;

import com.loggerproject.coreservice.data.model.tag.TagModel;
import com.loggerproject.coreservice.data.repository.TagModelRepository;
import com.loggerproject.coreservice.service.aglobal.create.AGlobalModelCreateService;
import com.loggerproject.coreservice.service.tag.delete.TagModelDeleteService;
import com.loggerproject.coreservice.service.tag.get.TagModelGetService;
import com.loggerproject.coreservice.service.tag.update.TagModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;

@Service
public class TagModelCreateService extends AGlobalModelCreateService<TagModel> {

    @Autowired
    TagModelGetService tagModelGetService;

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
        Assert.isTrue(CollectionUtils.isEmpty(model.getLogIDs()), "TagModel.logIDs must be empty");
        model.setLogIDs(new HashSet<>());

        model.getMetadata().setName(scrubAndValidateMetadataName(model));

        return model;
    }

    private String scrubAndValidateMetadataName(TagModel model) throws Exception {
        String name = model.getMetadata().getName();
        if (name == null) {
            throw new Exception("TagFileData.metadata.name must not be empty");
        }
        if (name.trim().length() != name.length()) {
            throw new Exception("TagFileData.metadata.name must not have leading and/or trailing whitespaces");
        }
        name = name.trim();
        if (name.length() == 0) {
            throw new Exception("TagFileData.metadata.name must not be empty");
        }
        List<TagModel> list = tagModelGetService.findByName(name);
        if (list.size() > 0) {
            throw new Exception("TagFileData.metadata.name: '" + name + "' already exists: '" + list.get(0).getId() + "'");
        }
        return name;
    }
}
