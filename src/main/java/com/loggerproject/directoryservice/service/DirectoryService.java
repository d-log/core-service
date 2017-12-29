package com.loggerproject.directoryservice.service;

import com.loggerproject.directoryservice.data.model.DirectoryModel;
import com.loggerproject.directoryservice.data.repository.DirectoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DirectoryService {

    @Autowired
    DirectoryRepository directoryRepository;

    public DirectoryModel findOne(String directoryID) {
        return directoryRepository.findOne(directoryID);
    }

    public String create(List<String> paths, List<String> tags) {
        DirectoryModel directoryModel = new DirectoryModel();

        paths = paths == null ? new ArrayList<>() : paths;
        tags  = tags  == null ? new ArrayList<>() : tags;

        directoryModel.setPathIDs(paths);
        directoryModel.setTagIDs(tags);

        directoryRepository.save(directoryModel);

        return directoryModel.getId();
    }
}
