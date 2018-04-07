package com.loggerproject.coreservice.endpoint.api.view;

import com.loggerproject.coreservice.data.document.view.Template;
import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.service.data.view.create.ViewModelCreateService;
import com.loggerproject.coreservice.service.data.view.delete.ViewModelDeleteService;
import com.loggerproject.coreservice.service.data.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.data.view.update.ViewModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.endpoint.api.EmptiableResources;
import com.loggerproject.microserviceglobalresource.server.endpoint.api.GlobalModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/view")
public class ViewModelRestController extends GlobalModelController<ViewModel> {

    @Autowired
    ViewModelUpdateService viewModelUpdateService;

    @Autowired
    public ViewModelRestController(ViewModelCreateService globalServerCreateService,
                                   ViewModelDeleteService globalServerDeleteService,
                                   ViewModelGetService globalServerGetService,
                                   ViewModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @PutMapping(value = {"/{view-id}/data-schema-json"}, produces = {"application/hal+json"})
    public ResponseEntity<?> updateDataSchemaJSON(@PathVariable("view-id") String viewID, @RequestBody String json) throws Exception {
        ViewModel modelUpdated = viewModelUpdateService.updateDataSchemaJSON(viewID, json);
        return hateosResponseEntityBuilder(modelUpdated, linkTo(methodOn(getClass()).updateDataSchemaJSON(viewID, json)).withSelfRel());
    }

    @PutMapping(value = {"/{view-id}/default-template-name/{view-template-name}"}, produces = {"application/hal+json"})
    public ResponseEntity<?> updateDefaultTemplateName(@PathVariable("view-id") String viewID, @PathVariable("view-template-name") String viewTemplateName) throws Exception {
        ViewModel modelUpdated = viewModelUpdateService.updateDefaultViewTemplateName(viewID, viewTemplateName);
        return hateosResponseEntityBuilder(modelUpdated, linkTo(methodOn(getClass()).updateDefaultTemplateName(viewID, viewTemplateName)).withSelfRel());
    }

    @PutMapping(value = {"/{view-id}/templates/add/{view-template-name}"}, produces = {"application/hal+json"})
    public ResponseEntity<?> addTemplate(@PathVariable("view-id") String id, @PathVariable("view-template-name") String viewTemplateName, @RequestBody Template template) throws Exception {
        ViewModel modelUpdated = viewModelUpdateService.addTemplate(id, viewTemplateName, template);
        return hateosResponseEntityBuilder(modelUpdated, linkTo(methodOn(getClass()).addTemplate(id, viewTemplateName, template)).withSelfRel());
    }

    @PutMapping(value = {"/{view-id}/templates/{view-template-name}"}, produces = {"application/hal+json"})
    public ResponseEntity<?> updateTemplate(@PathVariable("view-id") String id, @PathVariable("view-template-name") String viewTemplateName, @RequestBody Template template) throws Exception {
        ViewModel modelUpdated = viewModelUpdateService.updateTemplate(id, viewTemplateName, template);
        return hateosResponseEntityBuilder(modelUpdated, linkTo(methodOn(getClass()).updateTemplate(id, viewTemplateName, template)).withSelfRel());
    }

    @SuppressWarnings(value = "unchecked")
    private ResponseEntity<?> hateosResponseEntityBuilder(ViewModel model, Link link) {
        Resources<ViewModel> resources = new EmptiableResources(ViewModel.class, Collections.singletonList(model), link);
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }
}
