package com.loggerproject.coreservice.data.tag.endpoint;

import com.loggerproject.coreservice.data.tag.service.TagModelService;
import com.loggerproject.coreservice.data.tag.model.TagModel;
import com.loggerproject.microserviceglobalresource.server.controller.GlobalModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RepositoryRestController
public class TagModelRepositoryRestController extends GlobalModelController<TagModel> {

    @Autowired
    public TagModelRepositoryRestController(TagModelService tagService) {
        super(tagService);
    }

    @PostMapping(value = "/tag", produces="application/hal+json")
    public ResponseEntity save(@RequestBody TagModel model) throws Exception {
        return super.save(model);
    }

    @PutMapping(value = "/tag/{id}", produces="application/hal+json")
    public ResponseEntity update(@PathVariable("id") String id, @RequestBody TagModel model) throws Exception {
        return super.update(id, model);
    }
}
