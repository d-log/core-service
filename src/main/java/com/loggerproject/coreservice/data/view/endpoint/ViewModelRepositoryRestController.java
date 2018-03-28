package com.loggerproject.coreservice.data.view.endpoint;

import com.loggerproject.coreservice.data.view.ViewModelService;
import com.loggerproject.coreservice.data.view.model.ViewModel;
import com.loggerproject.microserviceglobalresource.server.controller.GlobalModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RepositoryRestController
public class ViewModelRepositoryRestController extends GlobalModelController<ViewModel> {

    @Autowired
    public ViewModelRepositoryRestController(ViewModelService service) {
        super(service);
    }

    @PostMapping(value = "/view", produces="application/hal+json")
    public ResponseEntity<?> save(@RequestBody ViewModel model) throws Exception {
        return super.save(model);
    }

    @PutMapping(value = "/view/{id}", produces="application/hal+json")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody ViewModel model) throws Exception {
        return super.update(id, model);
    }
}
