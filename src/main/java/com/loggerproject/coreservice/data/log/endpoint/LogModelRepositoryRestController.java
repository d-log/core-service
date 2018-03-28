package com.loggerproject.coreservice.data.log.endpoint;

import com.loggerproject.coreservice.data.directory.model.DirectoryModel;
import com.loggerproject.coreservice.data.log.LogModelService;
import com.loggerproject.coreservice.data.log.model.LogModel;
import com.loggerproject.microserviceglobalresource.server.controller.GlobalModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RepositoryRestController
public class LogModelRepositoryRestController extends GlobalModelController<LogModel> {

    @Autowired
    public LogModelRepositoryRestController(LogModelService service) {
        super(service);
    }

    @PostMapping(value = "/log", produces="application/hal+json")
    public ResponseEntity<?> save(@RequestBody LogModel model) throws Exception {
        return super.save(model);
    }

    @PutMapping(value = "/log/{id}", produces="application/hal+json")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody LogModel model) throws Exception {
        return super.update(id, model);
    }
}
