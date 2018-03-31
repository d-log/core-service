package com.loggerproject.coreservice.service.data.directory.update;

import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.data.repository.DirectoryModelRepositoryRestResource;
import com.loggerproject.coreservice.service.data.directory.create.DirectoryModelCreateService;
import com.loggerproject.coreservice.service.data.directory.delete.DirectoryModelDeleteService;
import com.loggerproject.coreservice.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.service.data.log.get.LogModelGetService;
import com.loggerproject.microserviceglobalresource.server.service.update.GlobalServerUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class DirectoryModelUpdateService extends GlobalServerUpdateService<DirectoryModel> {

    @Autowired
    DirectoryModelRepositoryRestResource directoryModelRepositoryRestResource;

    @Autowired
    LogModelGetService logModelGetService;

    @Autowired
    public DirectoryModelUpdateService(DirectoryModelRepositoryRestResource repository,
                                       @Lazy DirectoryModelCreateService globalServerCreateService,
                                       @Lazy DirectoryModelDeleteService globalServerDeleteService,
                                       @Lazy DirectoryModelGetService globalServerGetService,
                                       @Lazy DirectoryModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public void changeName(String id, String name) throws Exception {
        DirectoryModel model = (DirectoryModel)globalServerGetService.validateAndFindOne(id);
        model.setName(name);
        update(model);
    }

    public void assignFromParentToParent(String childID, String oldParentID, String newParentID) throws Exception {
        DirectoryModel child = (DirectoryModel)globalServerGetService.validateAndFindOne(childID);
        DirectoryModel oldParent = (DirectoryModel)globalServerGetService.validateAndFindOne(oldParentID);
        DirectoryModel newParent = (DirectoryModel)globalServerGetService.validateAndFindOne(newParentID);

        child.getParentIDs().remove(oldParentID);
        oldParent.getChildrenIDs().remove(childID);

        child.getParentIDs().add(newParentID);
        newParent.getChildrenIDs().add(childID);

        update(child);
        update(oldParent);
        update(newParent);
    }

    public void assignAdditionalParent(String childID, String parentID) throws Exception {
        DirectoryModel child = (DirectoryModel)globalServerGetService.validateAndFindOne(childID);
        DirectoryModel parent = (DirectoryModel)globalServerGetService.validateAndFindOne(parentID);

        child.getParentIDs().add(parentID);
        parent.getChildrenIDs().add(childID);

        update(child);
        update(parent);
    }
}
