package com.loggerproject.coreservice.service.directory.create;

import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.data.repository.DirectoryModelRepositoryRestResource;
import com.loggerproject.coreservice.service.directory.delete.DirectoryModelDeleteService;
import com.loggerproject.coreservice.service.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.service.directory.update.DirectoryModelUpdateService;
import com.loggerproject.coreservice.service.log.get.LogModelGetService;
import com.loggerproject.microserviceglobalresource.server.service.create.GlobalServerCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
public class DirectoryModelCreateService extends GlobalServerCreateService<DirectoryModel> {

    @Autowired
    DirectoryModelRepositoryRestResource directoryModelRepositoryRestResource;

    @Autowired
    LogModelGetService logModelGetService;

    @Autowired
    public DirectoryModelCreateService(DirectoryModelRepositoryRestResource repository,
                                       @Lazy DirectoryModelCreateService globalServerCreateService,
                                       @Lazy DirectoryModelDeleteService globalServerDeleteService,
                                       @Lazy DirectoryModelGetService globalServerGetService,
                                       @Lazy DirectoryModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public void scrubAndValidate(DirectoryModel model) throws Exception {
        model.setLogIDs(model.getLogIDs() == null ? new HashSet<>() : model.getLogIDs());
        model.setParentIDs(model.getParentIDs()  == null ? new HashSet<>() : model.getParentIDs());
        model.setChildrenIDs(model.getChildrenIDs()  == null ? new HashSet<>() : model.getChildrenIDs());
        model.setName(model.getName() == null ? "" : model.getName());
        model.setDescription(model.getDescription() == null ? "" : model.getDescription());

        logModelGetService.validateIds(model.getLogIDs());
        globalServerGetService.validateIds(model.getChildrenIDs());
        globalServerGetService.validateIds(model.getParentIDs());
    }

    @Override
    protected void beforeSave(DirectoryModel model) throws Exception {
        scrubAndValidate(model);
        super.beforeSave(model);
    }
}
