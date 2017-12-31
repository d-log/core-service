package com.loggerproject.directoryservice.client.service;

import com.loggerproject.directoryservice.server.controller.api.model.create.RequestCreateDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.create.ResponseCreateDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.delete.RequestDeleteDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.delete.ResponseDeleteDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.get.RequestGetDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.get.ResponseGetDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.update.RequestUpdateDirectoryModels;
import com.loggerproject.directoryservice.server.controller.api.model.update.ResponseUpdateDirectoryModels;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DirectoryServiceClient {

    @Autowired
    RestTemplate restTemplate;

    private static final String END_POINT = "http://DIRECTORY-SERVICE";

    public ResponseCreateDirectoryModels create(RequestCreateDirectoryModels request) {
        return restTemplate.postForObject(END_POINT + "/api/create", request, ResponseCreateDirectoryModels.class);
    }

    public ResponseUpdateDirectoryModels update(RequestUpdateDirectoryModels request) {
        return restTemplate.postForObject(END_POINT + "/api/update", request, ResponseUpdateDirectoryModels.class);
    }

    public ResponseGetDirectoryModels get(RequestGetDirectoryModels request) {
        return restTemplate.postForObject(END_POINT + "api/get", request, ResponseGetDirectoryModels.class);
    }

    public ResponseDeleteDirectoryModels delete(RequestDeleteDirectoryModels request) {
        return restTemplate.postForObject(END_POINT + "api/delete", request, ResponseDeleteDirectoryModels.class);
    }
}
