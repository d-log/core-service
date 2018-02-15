package com.loggerproject.coreservice.client.service;

import org.springframework.stereotype.Service;

@Service
public class CoreClientService {

    // These are based on core-service's application.properties which are provided by the config-server
    private static final String SCHEME_HOSTNAME = "http://CORE-SERVICE";
    private static final String SERVER_CONTEXT_PATH = "";               // based on 'server.context-path'
    private static final String ROOT_END_POINT = SCHEME_HOSTNAME + SERVER_CONTEXT_PATH;

    public CoreClientService() {
    }
}
