package com.loggerproject.coreservice.data.viewtemplate.endpoint;

import com.loggerproject.coreservice.data.viewtemplate.ViewTemplateModelService;
import com.loggerproject.coreservice.data.viewtemplate.model.ViewTemplateModel;
import com.loggerproject.microserviceglobalresource.server.controller.GlobalModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RepositoryRestController
public class ViewTemplateModelRepositoryRestController extends GlobalModelController<ViewTemplateModel> {

    @Autowired
    public ViewTemplateModelRepositoryRestController(ViewTemplateModelService service) {
        super(service);
    }

    @PostMapping(value = "/view-template", produces="application/hal+json")
    public ResponseEntity<?> save(@RequestBody ViewTemplateModel model) throws Exception {
        return super.save(model);
    }

    @PutMapping(value = "/view-template/{id}", produces="application/hal+json")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody ViewTemplateModel model) throws Exception {
        return super.update(id, model);
    }
}
