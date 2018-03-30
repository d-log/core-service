package com.loggerproject.coreservice.service.directory.get;

import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.data.repository.DirectoryModelRepositoryRestResource;
import com.loggerproject.coreservice.service.directory.create.DirectoryModelCreateService;
import com.loggerproject.coreservice.service.directory.delete.DirectoryModelDeleteService;
import com.loggerproject.coreservice.service.directory.update.DirectoryModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.get.GlobalServerGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class DirectoryModelGetService extends GlobalServerGetService<DirectoryModel> {

    @Autowired
    DirectoryModelGetService directoryModelGetService;

    @Autowired
    DirectoryModelRepositoryRestResource directoryModelRepositoryRestResource;

    @Autowired
    public DirectoryModelGetService(DirectoryModelRepositoryRestResource repository,
                                    @Lazy DirectoryModelCreateService globalServerCreateService,
                                    @Lazy DirectoryModelDeleteService globalServerDeleteService,
                                    @Lazy DirectoryModelGetService globalServerGetService,
                                    @Lazy DirectoryModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public DirectoryModel findOne(String id) {
        return super.findOne(id);
    }

    public List<DirectoryModel> findByName(String name) {
        return directoryModelRepositoryRestResource.findByName(name);
    }

    public List<DirectoryModel> findChildren(String id, Integer level) throws Exception {
        List<DirectoryModel> children = new ArrayList<>();

        if (level > 0) {
            DirectoryModel model = this.validateAndFindOne(id);
            Set<String> childrenIDs = model.getChildrenIDs();

            for (String childID : childrenIDs) {
                DirectoryModel child = this.findOne(childID);
                children.add(child);
            }

            List<DirectoryModel> grandChildren = new ArrayList<>();
            for (DirectoryModel child : children) {
                grandChildren.addAll(findChildren(child.getID(), level--));
            }
        }

        return children;
    }

    public List<DirectoryModel> findParents(String id) throws Exception {
        DirectoryModel model = this.validateAndFindOne(id);
        Set<String> parentIDs = model.getParentIDs();

        List<DirectoryModel> parents = new ArrayList<>();
        for (String parentID : parentIDs) {
            DirectoryModel parent = this.findOne(parentID);
            parents.add(parent);
        }

        return parents;
    }
}
