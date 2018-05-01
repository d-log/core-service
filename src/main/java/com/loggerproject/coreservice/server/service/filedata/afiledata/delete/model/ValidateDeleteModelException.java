package com.loggerproject.coreservice.server.service.filedata.afiledata.delete.model;

import lombok.Data;

@Data
public class ValidateDeleteModelException extends Exception {

    String id;

    public ValidateDeleteModelException(String id, String message) {
        super(message);
        this.id = id;
    }
}
