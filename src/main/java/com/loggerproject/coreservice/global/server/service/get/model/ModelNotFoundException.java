package com.loggerproject.coreservice.global.server.service.get.model;

import lombok.Data;

@Data
public class ModelNotFoundException extends Exception {

    public ModelNotFoundException(String message) {
        super(message);
    }
}
