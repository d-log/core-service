package com.loggerproject.coreservice.service.data.directory.delete;

import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.data.repository.DirectoryModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.directory.create.DirectoryModelCreateService;
import com.loggerproject.coreservice.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.service.data.directory.update.DirectoryModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.delete.GlobalServerDeleteService;
import com.loggerproject.microserviceglobalresource.server.service.delete.model.ModelBoundedToLogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class DirectoryModelDeleteService extends GlobalServerDeleteService<DirectoryModel> {

    @Autowired
    DirectoryModelRepositoryRestResource directoryModelRepositoryRestResource;

    @Autowired
    public DirectoryModelDeleteService(DirectoryModelRepositoryRestResource repository,
                                       @Lazy DirectoryModelCreateService globalServerCreateService,
                                       @Lazy DirectoryModelDeleteService globalServerDeleteService,
                                       @Lazy DirectoryModelGetService globalServerGetService,
                                       @Lazy DirectoryModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public DirectoryModel validateDelete(DirectoryModel model) throws Exception {
        if(model.getLogIDs().size() > 0) {
            throw new ModelBoundedToLogException(model.getID(), model.getLogIDs());
        }
        return model;
    }

    @Override
    public DirectoryModel beforeDelete(DirectoryModel model) throws Exception {
        model = validateDelete(model);
        return super.beforeDelete(model);
    }
}
