package com.loggerproject.coreservice.data.directory.endpoint;

import com.loggerproject.coreservice.data.directory.DirectoryModelService;
import com.loggerproject.coreservice.data.directory.model.DirectoryModel;
import com.loggerproject.microserviceglobalresource.server.controller.GlobalBPAController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @BasePathAwareController - overrides some of the @RepositoryRestController methods in order to give finer control
 * @RequestMapping - prepends path value to every method in this class
 */
@BasePathAwareController
@RequestMapping("/directory")
public class DirectoryBPAController extends GlobalBPAController<DirectoryModel> {

    @Autowired
    public DirectoryBPAController(DirectoryModelService directoryService) {
        super(directoryService);
    }
}
