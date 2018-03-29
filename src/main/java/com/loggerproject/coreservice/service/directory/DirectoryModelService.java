package com.loggerproject.coreservice.service.directory;

import com.loggerproject.coreservice.data.repository.DirectoryModelRepositoryRestResource;
import com.loggerproject.coreservice.data.model.directory.DirectoryModel;
import com.loggerproject.microserviceglobalresource.server.service.GlobalServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryModelService extends GlobalServerService<DirectoryModel> {

    @Autowired
    DirectoryModelRepositoryRestResource repository;

    @Autowired
    public DirectoryModelService(DirectoryModelRepositoryRestResource repository) {
        super(repository);
    }

    public List<DirectoryModel> findByName(String name) {
        return this.repository.findByName(name);
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



