package com.loggerproject.coreservice.service.directory.delete;

import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.data.repository.DirectoryModelRepositoryRestResource;
import com.loggerproject.coreservice.service.directory.create.DirectoryModelCreateService;
import com.loggerproject.coreservice.service.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.service.directory.update.DirectoryModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.delete.GlobalServerDeleteService;
import com.loggerproject.microserviceglobalresource.server.service.delete.model.DeleteAllResponse;
import com.loggerproject.microserviceglobalresource.server.service.delete.model.ModelBoundedToLogException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public void canDelete(String id) throws Exception {
        DirectoryModel model = (DirectoryModel) globalServerGetService.validateAndFindOne(id);
        if(model.getLogIDs().size() > 0) {
            throw new ModelBoundedToLogException(id, model.getLogIDs());
        }
    }

    @Override
    public void beforeDelete(String id) throws Exception {
        canDelete(id);
        super.beforeDelete(id);
    }
}
