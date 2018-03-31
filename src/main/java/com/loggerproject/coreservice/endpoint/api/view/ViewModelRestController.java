package com.loggerproject.coreservice.endpoint.api.view;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.service.data.view.manager.view.create.ViewModelCreateService;
import com.loggerproject.coreservice.service.data.view.manager.view.delete.ViewModelDeleteService;
import com.loggerproject.coreservice.service.data.view.manager.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.data.view.manager.view.update.ViewModelUpdateService;
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

    @PutMapping(value = {"/{id}/data-schema-json"}, produces = {"application/hal+json"})
    public ResponseEntity<?> updateDataSchemaJSON(@PathVariable("id") String id, @RequestBody String json) throws Exception {
        ViewModel modelUpdated = viewModelUpdateService.updateDataSchemaJSON(id, json);
        return hateosResponseEntityBuilder(modelUpdated, linkTo(methodOn(getClass()).updateDataSchemaJSON(id, json)).withSelfRel());
    }

    @PutMapping(value = {"/{id}/default-template/{view-template-id}"}, produces = {"application/hal+json"})
    public ResponseEntity<?> updateDefaultViewTemplateID(@PathVariable("id") String id, @PathVariable("view-template-id") String viewTemplateID) throws Exception {
        ViewModel modelUpdated = viewModelUpdateService.updateDefaultViewTemplateID(id, viewTemplateID);
        return hateosResponseEntityBuilder(modelUpdated, linkTo(methodOn(getClass()).updateDefaultViewTemplateID(id, viewTemplateID)).withSelfRel());
    }

    @SuppressWarnings(value = "unchecked")
    private ResponseEntity<?> hateosResponseEntityBuilder(ViewModel model, Link link) {
        Resources<ViewModel> resources = new EmptiableResources(ViewModel.class, Collections.singletonList(model), link);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(resources);
    }
}
