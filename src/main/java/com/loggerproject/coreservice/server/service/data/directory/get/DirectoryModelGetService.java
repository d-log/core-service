package com.loggerproject.coreservice.server.service.data.directory.get;

import com.loggerproject.coreservice.global.server.service.get.GlobalServerGetService;
import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.data.repository.DirectoryModelRepository;
import com.loggerproject.coreservice.server.service.data.directory.create.DirectoryModelCreateService;
import com.loggerproject.coreservice.server.service.data.directory.delete.DirectoryModelDeleteService;
import com.loggerproject.coreservice.server.service.data.directory.update.DirectoryModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
    DirectoryModelRepository directoryModelRepository;

    @Autowired
    public DirectoryModelGetService(DirectoryModelRepository repository,
                                    @Lazy DirectoryModelCreateService globalServerCreateService,
                                    @Lazy DirectoryModelDeleteService globalServerDeleteService,
                                    @Lazy DirectoryModelGetService globalServerGetService,
                                    @Lazy DirectoryModelUpdateService globalServerUpdateService,
                                    @Value("${spring.data.rest.maxPageSize}") Integer maxPageSize) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService, maxPageSize);
    }

    public List<DirectoryModel> findByName(String name) {
        return directoryModelRepository.findByName(name);
    }

    public List<DirectoryModel> findChildren(String id) throws Exception {
        return findChildren(id, 1);
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
