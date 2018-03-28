package com.loggerproject.coreservice.data.tag;

import com.loggerproject.coreservice.data.tag.endpoint.TagModelRepositoryRestResource;
import com.loggerproject.coreservice.data.tag.model.TagModel;
import com.loggerproject.microserviceglobalresource.server.service.GlobalServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TagModelService extends GlobalServerService<TagModel> {

    @Autowired
    public TagModelService(TagModelRepositoryRestResource repository) {
        super(repository);
    }

    @Override
    protected TagModel scrubAndValidate(TagModel model) throws Exception {
        model.setLogIDs(model.getLogIDs() != null ? model.getLogIDs() : new ArrayList());
        model.setName(model.getName() == null ? "" : model.getName());
        return model;
    }
}



