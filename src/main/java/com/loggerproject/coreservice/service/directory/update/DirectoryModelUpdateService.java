package com.loggerproject.coreservice.service.directory.update;

import com.loggerproject.coreservice.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.service.directory.DirectoryModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectoryModelUpdateService {

    @Autowired
    DirectoryModelService directoryModelService;

    public DirectoryModel assignFromParentToParent(String childID, String oldParentID, String newParentID) {
        DirectoryModel child =
    }

    public DirectoryModel assign
}
