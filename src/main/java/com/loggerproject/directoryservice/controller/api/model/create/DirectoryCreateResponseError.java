package com.loggerproject.directoryservice.controller.api.model.create;

import com.loggerproject.directoryservice.controller.api.model.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@Data
public class DirectoryCreateResponseError extends Response {

    Boolean success;
    String errorMessage;
}
