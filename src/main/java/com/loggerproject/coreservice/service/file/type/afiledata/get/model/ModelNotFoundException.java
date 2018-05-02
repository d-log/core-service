package com.loggerproject.coreservice.service.file.type.afiledata.get.model;

import lombok.Data;

@Data
public class ModelNotFoundException extends Exception {

    public ModelNotFoundException(String message) {
        super(message);
    }
}
