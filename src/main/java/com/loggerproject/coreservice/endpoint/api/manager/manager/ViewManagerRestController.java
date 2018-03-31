package com.loggerproject.coreservice.endpoint.api.manager.manager;

import com.loggerproject.coreservice.service.data.view.manager.manager.ViewManagerService;
import com.loggerproject.coreservice.service.data.view.manager.manager.model.CreateViewTemplateViewRequest;
import com.loggerproject.coreservice.service.data.view.manager.manager.model.CreateViewTemplateViewResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/view-manager")
public class ViewManagerRestController {

    @Autowired
    ViewManagerService viewManagerService;

    @SuppressWarnings(value = "unchecked")
    @PostMapping(produces = {"application/hal+json"})
    public ResponseEntity<?> save(@RequestBody CreateViewTemplateViewRequest request) throws Exception {
        CreateViewTemplateViewResponse response = viewManagerService.createViewTemplateViewRequest(request);
        Resources resources = new Resources(Collections.singletonList(response));
        resources.add(linkTo(methodOn(getClass()).save(request)).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(resources);
    }
}
