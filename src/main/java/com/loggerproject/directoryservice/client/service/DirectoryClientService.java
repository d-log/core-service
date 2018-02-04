package com.loggerproject.directoryservice.client.service;

import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import com.loggerproject.microserviceglobalresource.client.service.GlobalClientService;
import org.springframework.stereotype.Service;

@Service
public class DirectoryClientService extends GlobalClientService<DirectoryModel> {

    // These are based on directory-service's application.properties which are provided by the config-server
    private static final String SCHEME_HOSTNAME = "http://DIRECTORY-SERVICE";
    private static final String SERVER_CONTEXT_PATH = "";               // based on 'server.context-path'
    private static final String ROOT_END_POINT = SCHEME_HOSTNAME + SERVER_CONTEXT_PATH;

    private static final String SPRING_DATA_REST_BASE_PATH = "/api";    // based on 'spring.data.rest.base-path'
    private static final String REPOSITORY_REST_RESOURCE = ROOT_END_POINT + SPRING_DATA_REST_BASE_PATH + "/directory/";

    public DirectoryClientService() {
        super.setRepositoryRestResourceEndpoint(REPOSITORY_REST_RESOURCE);
    }
}
