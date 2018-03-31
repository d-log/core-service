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
import org.springframework.util.Assert;
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

    private void scrubAndValidate(DirectoryModel model) throws Exception {
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
    }

    private void updateOtherDocuments(DirectoryModel model) throws Exception {
        for(String parentID : model.getParentIDs()) {
            DirectoryModel parent = (DirectoryModel)globalServerGetService.findOne(parentID);
            parent.getChildrenIDs().add(model.getID());
            directoryModelUpdateService.update(parent);
        }
    }

    @Override
    protected void beforeSave(DirectoryModel model) throws Exception {
        scrubAndValidate(model);
        updateOtherDocuments(model);
        super.beforeSave(model);
    }
}
