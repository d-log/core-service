package com.loggerproject.coreservice.client.service;

import com.loggerproject.coreservice.server.controller.api.model.LogDetailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CoreClientService {

    // These are based on core-service's application.properties which are provided by the config-server
    private static final String SCHEME_HOSTNAME = "http://CORE-SERVICE";
    private static final String SERVER_CONTEXT_PATH = "";               // based on 'server.context-path'
    private static final String ROOT_END_POINT = SCHEME_HOSTNAME + SERVER_CONTEXT_PATH;

    @Autowired
    @Qualifier("globalRestTemplate")
    protected RestTemplate restTemplate;

    public LogDetailModel findOne(String id) {
        return this.restTemplate.getForObject(ROOT_END_POINT + "/api/log-detail/" + id, LogDetailModel.class);
    }
}
