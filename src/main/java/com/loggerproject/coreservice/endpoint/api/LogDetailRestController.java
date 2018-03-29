package com.loggerproject.coreservice.endpoint.api;

import com.loggerproject.coreservice.service.log.get.detail.model.LogDetailModel;
import com.loggerproject.coreservice.service.log.get.detail.LogDetailModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.DummyInvocationUtils;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/log-detail")
public class LogDetailRestController {

    @Autowired
    LogDetailModelService logDetailModelService;

    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    public ResponseEntity<?> getLogDetailModel(@PathVariable("id") String id) throws Exception {
        LogDetailModel logDetailModel = logDetailModelService.findOne(id);
        Resources resources = new Resources<>(Collections.singletonList(logDetailModel));
        resources.add(ControllerLinkBuilder.linkTo((DummyInvocationUtils.methodOn(this.getClass())).getLogDetailModel(id)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }
}
