package com.loggerproject.coreservice.endpoint.api;

import com.loggerproject.coreservice.service.logdetail.model.LogDetailModel;
import com.loggerproject.coreservice.service.logdetail.LogDetailModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.core.DummyInvocationUtils;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/api/log")
public class LogRestController {

    @Autowired
    LogDetailModelService logDetailModelService;

//    @Autowired
//    LogClientService logClientService;
//
//    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
//    public ResponseEntity<?> getLogModel(@PathVariable("id") String id) throws Exception {
//        LogModel model = logClientService.validateAndFindOne(id);
//        Resources resources = new Resources<>(Collections.singletonList(model));
//        resources.add(ControllerLinkBuilder.linkTo((DummyInvocationUtils.methodOn(this.getClass())).getLogModel(id)).withSelfRel());
//        return ResponseEntity.status(HttpStatus.OK).body(resources);
//    }

    @GetMapping(value = "/detail/{id}", produces = {"application/hal+json"})
    public ResponseEntity<?> getLogDetailModel(@PathVariable("id") String id) throws Exception {
        LogDetailModel logDetailModel = logDetailModelService.findOne(id);
        Resources resources = new Resources<>(Collections.singletonList(logDetailModel));
        resources.add(ControllerLinkBuilder.linkTo((DummyInvocationUtils.methodOn(this.getClass())).getLogDetailModel(id)).withSelfRel());
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<?> handleException(Exception e) throws Exception {
        throw e;
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
