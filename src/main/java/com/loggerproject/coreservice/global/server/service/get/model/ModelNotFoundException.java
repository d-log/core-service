package com.loggerproject.coreservice.global.server.service.get.model;

import lombok.Data;

@Data
public class ModelNotFoundException extends Exception {

    String id;

    public ModelNotFoundException(String id, String message) {
        super(message);
        this.id = id;
    }
}
