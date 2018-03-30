package com.loggerproject.coreservice.endpoint.api.directory;

import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.service.directory.create.DirectoryModelCreateService;
import com.loggerproject.coreservice.service.directory.delete.DirectoryModelDeleteService;
import com.loggerproject.coreservice.service.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.service.directory.update.DirectoryModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.endpoint.api.GlobalModelController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/directory")
public class DirectoryModelRestController extends GlobalModelController<DirectoryModel> {

    @Autowired
    public DirectoryModelRestController(DirectoryModelCreateService globalServerCreateService,
                                        DirectoryModelDeleteService globalServerDeleteService,
                                        DirectoryModelGetService globalServerGetService,
                                        DirectoryModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
