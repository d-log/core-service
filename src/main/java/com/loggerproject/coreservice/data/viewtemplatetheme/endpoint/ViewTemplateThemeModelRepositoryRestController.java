package com.loggerproject.coreservice.data.viewtemplatetheme.endpoint;

import com.loggerproject.coreservice.data.viewtemplatetheme.ViewTemplateThemeModelService;
import com.loggerproject.coreservice.data.viewtemplatetheme.model.ViewTemplateThemeModel;
import com.loggerproject.microserviceglobalresource.server.controller.GlobalModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RepositoryRestController
public class ViewTemplateThemeModelRepositoryRestController extends GlobalModelController<ViewTemplateThemeModel> {

    @Autowired
    public ViewTemplateThemeModelRepositoryRestController(ViewTemplateThemeModelService service) {
        super(service);
    }

    @PostMapping(value = "/view-template-theme", produces="application/hal+json")
    public ResponseEntity<?> save(@RequestBody ViewTemplateThemeModel model) throws Exception {
        return super.save(model);
    }

    @PutMapping(value = "/view-template-theme/{id}", produces="application/hal+json")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody ViewTemplateThemeModel model) throws Exception {
        return super.update(id, model);
    }
}
