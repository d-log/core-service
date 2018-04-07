package com.loggerproject.coreservice.server;

import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class TestModelRestController {

    @GetMapping(value = "/bad", produces = {"application/hal+json"})
    public ResponseEntity<?> bad() {
        LogModel t = generateModel();
        Resources<LogModel> resources = new Resources(Collections.singletonList(t));
        return ResponseEntity.status(HttpStatus.OK).body(resources);
    }

    @GetMapping(value = "/good")
    public ResponseEntity<LogModel> good() {
        LogModel t = generateModel();
        return ResponseEntity.ok(t);
    }

    private LogModel generateModel() {
        LogModel t = new LogModel();
        ImageInternalLogData i = new ImageInternalLogData();
        t.setLogData(i);
        return t;
    }
}
