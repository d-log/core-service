package com.loggerproject.coreservice.server.service.data.directory;

import com.loggerproject.coreservice.global.server.document.model.MetaData;
import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.data.repository.DirectoryModelRepository;
import com.loggerproject.coreservice.server.service.data.directory.create.DirectoryModelCreateService;
import com.loggerproject.coreservice.server.service.data.directory.get.DirectoryModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
public class DirectoryRootService {

    public static final String ROOT_NAME = "ROOT_DIRECTORY";

    @Autowired
    DirectoryModelGetService directoryModelGetService;

    @Autowired
    DirectoryModelCreateService directoryModelCreateService;

    @Autowired
    DirectoryModelRepository directoryModelRepository;

    public DirectoryModel createRoot() throws Exception {
        List<DirectoryModel> list = directoryModelGetService.findByName(ROOT_NAME);
        // if anything is to be changed here then will need to manually update it
        if (list.isEmpty()) {
            DirectoryModel root = new DirectoryModel();
            root.setName(ROOT_NAME);
            root.setChildrenIDs(new HashSet<>());
            root.setDescription("This is the Root Directory");
            root.setLogIDs(new HashSet<>());
            root.setParentIDs(new HashSet<>());
            root.setMetadata(new MetaData());
            return directoryModelRepository.save(root);
        } else {
            throw new Exception("Root DirectoryModel already created. id: '" + list.get(0).getID() + "'");
        }
    }

    public void validateNotRoot(DirectoryModel model) throws Exception {
        if (model.getName().equals(ROOT_NAME)) {
            throw new Exception("DirectoryModel.name cannot be 'THE-SUPER-ROOT'");
        }
    }

    public DirectoryModel getRoot() {
        return directoryModelGetService.findByName(ROOT_NAME).get(0);
    }
}
