package com.loggerproject.coreservice.data.directory.endpoint;

import com.loggerproject.coreservice.data.directory.DirectoryModelService;
import com.loggerproject.coreservice.data.directory.model.DirectoryModel;
import com.loggerproject.microserviceglobalresource.server.controller.GlobalModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RepositoryRestController
public class DirectoryModelRepositoryRestController extends GlobalModelController<DirectoryModel> {

    @Autowired
    public DirectoryModelRepositoryRestController(DirectoryModelService service) {
        super(service);
    }

    @PostMapping(value = "/directory", produces="application/hal+json")
    public ResponseEntity<?> save(@RequestBody DirectoryModel model) throws Exception {
        return super.save(model);
    }

    @PutMapping(value = "/directory/{id}", produces="application/hal+json")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody DirectoryModel model) throws Exception {
        return super.update(id, model);
    }
}
