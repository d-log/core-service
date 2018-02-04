package com.loggerproject.directoryservice.server.service;

import com.loggerproject.directoryservice.server.controller.api.DirectoryRepositoryRestResource;
import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import com.loggerproject.microserviceglobalresource.server.service.GlobalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DirectoryService implements GlobalService<DirectoryModel> {

    @Autowired
    DirectoryRepositoryRestResource directoryRepository;

    private DirectoryModel scrubAndValidate(DirectoryModel model) {
        model.setLogIDs(model.getLogIDs() == null ? new ArrayList<>() : model.getLogIDs());
        model.setParentIDs(model.getParentIDs()  == null ? new ArrayList<>() : model.getParentIDs());
        model.setChildrenIDs(model.getChildrenIDs()  == null ? new ArrayList<>() : model.getChildrenIDs());
        model.setName(model.getName() == null ? "" : model.getName());
        model.setDescription(model.getDescription() == null ? "" : model.getDescription());

        return model;
    }

    public DirectoryModel save(DirectoryModel model) {
        model = scrubAndValidate(model);
        model.setID(null);
        directoryRepository.save(model);
        return model;
    }

    public DirectoryModel update(String id, DirectoryModel model) {
        model = scrubAndValidate(model);

        DirectoryModel oldModel = directoryRepository.findOne(id);
        if (oldModel != null) {
            oldModel.setLogIDs(model.getLogIDs());
            oldModel.setParentIDs(model.getParentIDs());
            oldModel.setChildrenIDs(model.getChildrenIDs());
            oldModel.setName(model.getName());
            oldModel.setDescription(model.getDescription());

            directoryRepository.save(oldModel);
        }

        return oldModel;
    }
}



