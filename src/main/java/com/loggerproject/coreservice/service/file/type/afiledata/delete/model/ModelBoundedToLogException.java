package com.loggerproject.coreservice.service.file.type.afiledata.delete.model;

import lombok.Data;

import java.util.Collection;

@Data
public class ModelBoundedToLogException extends ValidateDeleteModelException {

    Collection<String> boundedLogIDs;

    public ModelBoundedToLogException(String modelID, Collection<String> boundedLogIDs) {
        super(modelID, "Model: '" + modelID + "' bounded to log(s): " + boundedLogIDs.toString());
        this.boundedLogIDs = boundedLogIDs;
    }
}
