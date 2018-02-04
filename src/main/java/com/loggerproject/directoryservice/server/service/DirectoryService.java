package com.loggerproject.directoryservice.server.service;

import com.loggerproject.directoryservice.server.controller.api.DirectoryRepositoryRestResource;
import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import com.loggerproject.logservice.client.service.LogClientService;
import com.loggerproject.microserviceglobalresource.server.service.GlobalServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DirectoryService extends GlobalServerService<DirectoryModel> {

    @Autowired
    LogClientService logClientService;

    @Autowired
    public DirectoryService(DirectoryRepositoryRestResource repository) {
        super(repository);
    }

    @Override
    protected DirectoryModel scrubAndValidate(DirectoryModel model) throws Exception {
        model.setLogIDs(model.getLogIDs() == null ? new ArrayList<>() : model.getLogIDs());
        model.setParentIDs(model.getParentIDs()  == null ? new ArrayList<>() : model.getParentIDs());
        model.setChildrenIDs(model.getChildrenIDs()  == null ? new ArrayList<>() : model.getChildrenIDs());
        model.setName(model.getName() == null ? "" : model.getName());
        model.setDescription(model.getDescription() == null ? "" : model.getDescription());

        logClientService.validateIds(model.getLogIDs());
        this.validateIds(model.getChildrenIDs());
        this.validateIds(model.getParentIDs());

        return model;
    }

    @SuppressWarnings("unchecked")
    public DirectoryModel update(String id, DirectoryModel model) throws Exception {
        model = scrubAndValidate(model);

        DirectoryModel oldModel = (DirectoryModel)repository.findOne(id);
        if (oldModel != null) {
            oldModel.setLogIDs(model.getLogIDs());
            oldModel.setParentIDs(model.getParentIDs());
            oldModel.setChildrenIDs(model.getChildrenIDs());
            oldModel.setName(model.getName());
            oldModel.setDescription(model.getDescription());

            repository.save(oldModel);
        }

        return oldModel;
    }
}



