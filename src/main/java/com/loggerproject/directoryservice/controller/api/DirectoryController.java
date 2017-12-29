package com.loggerproject.directoryservice.controller.api;

import com.loggerproject.directoryservice.controller.api.model.create.DirectoryCreateRequest;
import com.loggerproject.directoryservice.controller.api.model.create.DirectoryCreateResponseError;
import com.loggerproject.directoryservice.controller.api.model.create.DirectoryCreateResponseSuccess;
import com.loggerproject.directoryservice.controller.api.model.Response;
import com.loggerproject.directoryservice.data.model.DirectoryModel;
import com.loggerproject.directoryservice.service.DirectoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
public class DirectoryController {

    @Autowired
    DirectoryService directoryService;

    @GetMapping(value = "/get/{directoryCreate}", produces = "application/json")
    public DirectoryModel getDirectoryModel(@PathVariable(value="directoryID") String directoryID) {
        return directoryService.findOne(directoryID);
    }

    @PostMapping(value = "/create", produces = "application/json")
    public Response createDirectoryModel(@RequestBody DirectoryCreateRequest request) {
        try {
            String logID = directoryService.create(
                    request.getPaths(),
                    request.getTags()
            );

            DirectoryCreateResponseSuccess successResponse = new DirectoryCreateResponseSuccess();
            successResponse.setLogID(logID);
            return successResponse;
        }
        catch (Exception e) {
            DirectoryCreateResponseError errorResponse = new DirectoryCreateResponseError();
            errorResponse.setErrorMessage(e.getMessage());
            return errorResponse;
        }
    }
}
