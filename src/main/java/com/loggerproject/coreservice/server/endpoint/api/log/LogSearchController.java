package com.loggerproject.coreservice.server.endpoint.api.log;

import com.loggerproject.coreservice.server.service.data.log.get.search.LogModelSearch;
import com.loggerproject.coreservice.server.service.data.log.get.search.model.SearchRequest;
import com.loggerproject.coreservice.server.service.data.log.get.search.model.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
@RequestMapping("/api/log-search")
public class LogSearchController {

    @Autowired
    LogModelSearch logModelSearch;

    @PostMapping(produces = {"application/hal+json"})
    public SearchResponse findLogs(@RequestBody SearchRequest request) throws Exception {
        SearchResponse response = logModelSearch.findLogs(request);
        response.add(linkTo(methodOn(getClass()).findLogs(request)).withSelfRel());
        return response;
    }
}
