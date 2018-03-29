package com.loggerproject.coreservice.service.directory.get;

import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.data.repository.DirectoryModelRepositoryRestResource;
import com.loggerproject.coreservice.service.directory.DirectoryModelService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class DirectoryModelGetService {

    @Autowired
    DirectoryModelRepositoryRestResource repository;

    @Autowired
    DirectoryModelService directoryModelService;

    public List<DirectoryModel> findByName(String name) {
        return this.repository.findByName(name);
    }

    public List<DirectoryModel> findAllChildren(String id, Integer level) throws Exception {
        List<DirectoryModel> children = new ArrayList<>();

        if (level > 0) {
            DirectoryModel model = directoryModelService.validateAndFindOne(id);
            List<String> childrenIDs = model.getChildrenIDs();

            for (String childID : childrenIDs) {
                DirectoryModel child = directoryModelService.findOne(childID);
                children.add(child);
            }

            List<DirectoryModel> grandChildren = new ArrayList<>();
            for (DirectoryModel child : children) {
                grandChildren.addAll(findAllChildren(child.getID(), level--));
            }
        }

        return children;
    }

    public List<DirectoryModel> findAllParents(String id) throws Exception {
        DirectoryModel model = directoryModelService.validateAndFindOne(id);
        List<String> parentIDs = model.getParentIDs();

        List<DirectoryModel> parents = new ArrayList<>();
        for (String parentID : parentIDs) {
            DirectoryModel parent = directoryModelService.findOne(parentID);
            parents.add(parent);
        }

        return parents;
    }
}
