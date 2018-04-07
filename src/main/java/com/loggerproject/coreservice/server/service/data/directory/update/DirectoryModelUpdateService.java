package com.loggerproject.coreservice.server.service.data.directory.update;

import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.data.repository.DirectoryModelRepository;
import com.loggerproject.coreservice.server.service.data.directory.create.DirectoryModelCreateService;
import com.loggerproject.coreservice.server.service.data.directory.delete.DirectoryModelDeleteService;
import com.loggerproject.coreservice.server.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.global.server.service.update.GlobalServerUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class DirectoryModelUpdateService extends GlobalServerUpdateService<DirectoryModel> {

    @Autowired
    DirectoryModelRepository directoryModelRepository;

    @Autowired
    public DirectoryModelUpdateService(DirectoryModelRepository repository,
                                       @Lazy DirectoryModelCreateService globalServerCreateService,
                                       @Lazy DirectoryModelDeleteService globalServerDeleteService,
                                       @Lazy DirectoryModelGetService globalServerGetService,
                                       @Lazy DirectoryModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public DirectoryModel changeName(String id, String name) throws Exception {
        DirectoryModel model = (DirectoryModel)globalServerGetService.validateAndFindOne(id);
        model.setName(name);
        model = update(model);
        return model;
    }

    public DirectoryModel assignFromParentToParent(String childID, String oldParentID, String newParentID) throws Exception {
        DirectoryModel model = (DirectoryModel)globalServerGetService.validateAndFindOne(childID);
        DirectoryModel oldParent = (DirectoryModel)globalServerGetService.validateAndFindOne(oldParentID);
        DirectoryModel newParent = (DirectoryModel)globalServerGetService.validateAndFindOne(newParentID);

        model.getParentIDs().remove(oldParentID);
        oldParent.getChildrenIDs().remove(childID);

        model.getParentIDs().add(newParentID);
        newParent.getChildrenIDs().add(childID);

        update(oldParent);
        update(newParent);
        model = update(model);

        return model;
    }

    public DirectoryModel assignAdditionalParent(String childID, String parentID) throws Exception {
        DirectoryModel model = (DirectoryModel)globalServerGetService.validateAndFindOne(childID);
        DirectoryModel parent = (DirectoryModel)globalServerGetService.validateAndFindOne(parentID);

        model.getParentIDs().add(parentID);
        parent.getChildrenIDs().add(childID);

        update(parent);
        update(model);

        return model;
    }
}
