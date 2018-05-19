package com.loggerproject.coreservice.service.aglobal.get.model;

import lombok.Data;

@Data
public class ModelNotFoundException extends Exception {

    public ModelNotFoundException(String message) {
        super(message);
    }
}
