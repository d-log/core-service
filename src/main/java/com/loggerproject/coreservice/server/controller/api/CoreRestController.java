package com.loggerproject.coreservice.server.controller.api;

import com.loggerproject.coreservice.server.controller.api.model.LogDetailModel;
import com.loggerproject.coreservice.server.service.CoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.DummyInvocationUtils;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api")
public class CoreRestController {

    @Autowired
    CoreService coreService;

    @GetMapping(value = "/log-detail/{id}", produces = {"application/hal+json"})
    public ResponseEntity<?> getLogDetail(@PathVariable("id") String id) {
        try {
            LogDetailModel logDetailModel = coreService.getLogDetailModel(id);
            Resources resources = new Resources<>(Collections.singletonList(logDetailModel));
            resources.add(ControllerLinkBuilder.linkTo((DummyInvocationUtils.methodOn(this.getClass())).getLogDetail(id)).withSelfRel());
            return ResponseEntity.status(HttpStatus.OK).body(resources);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }}
