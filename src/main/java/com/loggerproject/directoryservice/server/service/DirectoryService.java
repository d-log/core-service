package com.loggerproject.directoryservice.server.service;

import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import com.loggerproject.directoryservice.server.data.repository.DirectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DirectoryService {

    @Autowired
    DirectoryRepository directoryRepository;

    private DirectoryModel scrubModel(DirectoryModel model) {
        model.setLogIDs(model.getLogIDs() == null ? new ArrayList<>() : model.getLogIDs());
        model.setParentIDs(model.getParentIDs()  == null ? new ArrayList<>() : model.getParentIDs());
        model.setChildrenIDs(model.getChildrenIDs()  == null ? new ArrayList<>() : model.getChildrenIDs());
        model.setName(model.getName() == null ? "" : model.getName());
        model.setDescription(model.getDescription() == null ? "" : model.getDescription());
        return model;
    }

    public DirectoryModel create(DirectoryModel model) {
        model = scrubModel(model);
        model.setId(null);
        directoryRepository.save(model);

        return model;
    }

    public DirectoryModel update(DirectoryModel model) {
        model = scrubModel(model);

        DirectoryModel oldModel = directoryRepository.findOne(model.getId());
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

    public DirectoryModel getFindOne(String directoryID) {
        return directoryRepository.findOne(directoryID);
    }

    public DirectoryModel delete(String directoryID) {
        DirectoryModel model = getFindOne(directoryID);
    	directoryRepository.delete(directoryID);

        return model;
    }
}



