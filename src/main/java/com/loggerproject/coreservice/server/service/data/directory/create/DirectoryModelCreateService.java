package com.loggerproject.coreservice.server.service.data.directory.create;

import com.loggerproject.coreservice.global.server.service.create.GlobalServerCreateService;
import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.data.repository.DirectoryModelRepository;
import com.loggerproject.coreservice.server.service.data.directory.delete.DirectoryModelDeleteService;
import com.loggerproject.coreservice.server.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.server.service.data.directory.update.DirectoryModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;

@Service
public class DirectoryModelCreateService extends GlobalServerCreateService<DirectoryModel> {

    @Autowired
    DirectoryModelRepository directoryModelRepository;

    @Autowired
    DirectoryModelUpdateService directoryModelUpdateService;

    @Autowired
    public DirectoryModelCreateService(DirectoryModelRepository repository,
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
        for (String parentID : model.getParentIDs()) {
            DirectoryModel parent = (DirectoryModel) globalServerGetService.findOne(parentID);
            parent.getChildrenIDs().add(model.getID());
            directoryModelUpdateService.update(parent);
        }

        return model;
    }

    @Override
    protected DirectoryModel afterCreate(DirectoryModel model) throws Exception {
        model = updateOtherDocuments(model);
        return super.afterCreate(model);
    }
}
