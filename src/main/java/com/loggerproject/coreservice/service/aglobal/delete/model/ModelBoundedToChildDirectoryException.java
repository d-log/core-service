package com.loggerproject.coreservice.service.aglobal.delete.model;

import lombok.Data;

import java.util.Collection;

@Data
public class ModelBoundedToChildDirectoryException extends ValidateDeleteModelException {

    Collection<String> boundedChildDirectories;

    public ModelBoundedToChildDirectoryException(String modelID, Collection<String> boundedLogIDs) {
        super(modelID, "Model: '" + modelID + "' bounded to child directory(s): " + boundedLogIDs.toString());
        this.boundedChildDirectories = boundedLogIDs;
    }
}
