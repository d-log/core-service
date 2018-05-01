package com.loggerproject.coreservice.server.service.filedata.afiledata.get.model;

import lombok.Data;

@Data
public class ModelNotFoundException extends Exception {

    public ModelNotFoundException(String message) {
        super(message);
    }
}
