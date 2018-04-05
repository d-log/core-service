package com.loggerproject.coreservice.service.data.directory.create;

import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.data.repository.DirectoryModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.directory.delete.DirectoryModelDeleteService;
import com.loggerproject.coreservice.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.service.data.directory.update.DirectoryModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.create.GlobalServerCreateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;

@Service
public class DirectoryModelCreateService extends GlobalServerCreateService<DirectoryModel> {

    @Autowired
    DirectoryModelRepositoryRestResource directoryModelRepositoryRestResource;

    @Autowired
    DirectoryModelUpdateService directoryModelUpdateService;

    @Autowired
    public DirectoryModelCreateService(DirectoryModelRepositoryRestResource repository,
                                       @Lazy DirectoryModelCreateService globalServerCreateService,
                                       @Lazy DirectoryModelDeleteService globalServerDeleteService,
                                       @Lazy DirectoryModelGetService globalServerGetService,
                                       @Lazy DirectoryModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public DirectoryModel beforeSaveScrubAndValidate(DirectoryModel model) throws Exception {
        if (!CollectionUtils.isEmpty(model.getLogIDs())) {
            throw new Exception("DirectoryModel.logIDs should be empty");
        }
        if (!CollectionUtils.isEmpty(model.getChildrenIDs())) {
            throw new Exception("DirectoryModel.childrenIDs should be empty");
        }

        model.setLogIDs(new HashSet<>());
        model.setChildrenIDs(new HashSet<>());
        model.setParentIDs(model.getParentIDs() == null ? new HashSet<>() : model.getParentIDs());
        model.setName(model.getName() == null ? "" : model.getName());
        model.setDescription(model.getDescription() == null ? "" : model.getDescription());

        globalServerGetService.validateIds(model.getParentIDs());

        return model;
    }

    private DirectoryModel updateOtherDocuments(DirectoryModel model) throws Exception {
        for(String parentID : model.getParentIDs()) {
            DirectoryModel parent = (DirectoryModel)globalServerGetService.findOne(parentID);
            parent.getChildrenIDs().add(model.getID());
            directoryModelUpdateService.update(parent);
        }

        return model;
    }

    @Override
    protected DirectoryModel afterSave(DirectoryModel model) throws Exception {
        model = updateOtherDocuments(model);
        return super.afterSave(model);
    }
}
