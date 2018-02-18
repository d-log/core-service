package com.loggerproject.coreservice.server.controller.api;

import com.loggerproject.coreservice.server.model.view.manager.CreateViewModel;
import com.loggerproject.coreservice.server.service.viewmanager.ViewManagerService;
import com.loggerproject.viewtemplateservice.server.data.model.ViewTemplateModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.DummyInvocationUtils;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/view-manager")
public class ViewManagerRestController {

    @Autowired
    ViewManagerService viewManagerService;

    @PostMapping(value = "view", produces = {"application/hal+json"})
    public ResponseEntity<?> createView(@RequestBody CreateViewModel createViewModel) throws Exception {
        CreateViewModel createdModel = viewManagerService.createViewModel(createViewModel);
        Resources resources = new Resources<>(Collections.singletonList(createdModel));
        resources.add(ControllerLinkBuilder.linkTo((DummyInvocationUtils.methodOn(this.getClass())).createView(createViewModel)).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(resources);
    }

    @PostMapping(value = "/view-template", produces = {"application/hal+json"})
    public ResponseEntity<?> createViewTemplate(@RequestBody ViewTemplateModel viewTemplateModel) throws Exception {
        ViewTemplateModel createdModel = viewManagerService.createViewTemplateModel(viewTemplateModel);
        Resources resources = new Resources<>(Collections.singletonList(createdModel));
        resources.add(ControllerLinkBuilder.linkTo((DummyInvocationUtils.methodOn(this.getClass())).createViewTemplate(viewTemplateModel)).withSelfRel());
        return ResponseEntity.status(HttpStatus.CREATED).body(resources);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception e) throws Exception {
        throw e;
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
