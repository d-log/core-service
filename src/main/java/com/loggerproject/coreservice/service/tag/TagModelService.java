package com.loggerproject.coreservice.service.tag;

import com.loggerproject.coreservice.data.document.tag.TagModel;
import com.loggerproject.coreservice.data.repository.TagModelRepositoryRestResource;
import com.loggerproject.microserviceglobalresource.server.service.GlobalModelServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagModelService extends GlobalModelServerService<TagModel> {

    @Autowired
    TagModelRepositoryRestResource repository;

    @Autowired
    public TagModelService(TagModelRepositoryRestResource repository) {
        super(repository);
    }

    public List<TagModel> findByName(String name) {
        return this.repository.findByName(name);
    }

    @Override
    public void scrubAndValidate(TagModel model) {
        model.setLogIDs(model.getLogIDs() != null ? model.getLogIDs() : new ArrayList());
        model.setName(model.getName() == null ? "" : model.getName());
    }
}
