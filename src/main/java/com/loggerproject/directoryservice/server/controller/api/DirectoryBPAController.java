package com.loggerproject.directoryservice.server.controller.api;

import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import com.loggerproject.directoryservice.server.service.DirectoryService;
import com.loggerproject.microserviceglobalresource.server.controller.GlobalBPAController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.web.bind.annotation.*;

/**
 * @BasePathAwareController - overrides some of the @RepositoryRestController methods in order to give finer control
 * @RequestMapping - prepends path value to every method in this class
 */
@BasePathAwareController
@RequestMapping("/directory")
public class DirectoryBPAController extends GlobalBPAController<DirectoryModel> {

    @Autowired
    public DirectoryBPAController(DirectoryService directoryService) {
        super(directoryService);
    }
}
