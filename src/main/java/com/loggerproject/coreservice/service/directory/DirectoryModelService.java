package com.loggerproject.coreservice.service.directory;

import com.loggerproject.coreservice.data.repository.DirectoryModelRepositoryRestResource;
import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.microserviceglobalresource.server.service.GlobalModelServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class DirectoryModelService extends GlobalModelServerService<DirectoryModel> {

    @Autowired
    DirectoryModelRepositoryRestResource repository;

    @Autowired
    public DirectoryModelService(DirectoryModelRepositoryRestResource repository) {
        super(repository);
    }

    @Override
    public void scrubAndValidate(DirectoryModel model) throws Exception {
        model.setLogIDs(model.getLogIDs() == null ? new ArrayList<>() : model.getLogIDs());
        model.setParentIDs(model.getParentIDs()  == null ? new ArrayList<>() : model.getParentIDs());
        model.setChildrenIDs(model.getChildrenIDs()  == null ? new ArrayList<>() : model.getChildrenIDs());
        model.setName(model.getName() == null ? "" : model.getName());
        model.setDescription(model.getDescription() == null ? "" : model.getDescription());

        this.validateIds(model.getChildrenIDs());
        this.validateIds(model.getParentIDs());
    }
}



