package com.loggerproject.coreservice.global.server.service.delete.model;

import lombok.Data;

@Data
public class ValidateDeleteModelException extends Exception {

    String id;

    public ValidateDeleteModelException(String id, String message) {
        super(message);
        this.id = id;
    }
}
