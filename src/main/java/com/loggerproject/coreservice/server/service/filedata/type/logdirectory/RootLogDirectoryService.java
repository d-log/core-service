package com.loggerproject.coreservice.server.service.filedata.type.logdirectory;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.extra.organization.Organization;
import com.loggerproject.coreservice.server.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.server.data.document.file.extra.metadata.Metadata;
import com.loggerproject.coreservice.server.data.repository.FileModelRepository;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.create.LogDirectoryFileModelCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@Service
public class RootLogDirectoryService {

    public static final String ROOT_NAME = "ROOT_DIRECTORY";

    @Autowired
    LogDirectoryFileModelGetService directoryGetService;

    @Autowired
    LogDirectoryFileModelCreateService directoryCreateService;

    @Autowired
    FileModelRepository repository;

    public FileModel createRoot() throws Exception {
        List<FileModel> list = directoryGetService.findByName(ROOT_NAME);
        // if anything is to be changed here then will need to manually update it
        if (list.isEmpty()) {
            FileModel root = new FileModel();

            Metadata metadata = new Metadata();
            Date now = new Date();
            metadata.setName(ROOT_NAME);
            metadata.setDescription("This is the Root Directory");
            metadata.setCreated(now);
            metadata.setLastUpdated(now);
            metadata.setType(LogDirectoryFileData.class.getSimpleName());
            root.setMetadata(metadata);

            LogDirectoryFileData directory = new LogDirectoryFileData();
            directory.setChildLogDirectoryFileIDs(new HashSet<>());
            directory.setLogFileIDs(new HashSet<>());

            Organization organization = new Organization();
            organization.setParentLogDirectoryFileIDs(new HashSet<>());
            organization.setTagFileIDs(new HashSet<>());
            directory.setOrganization(organization);

            directory.setLogDatas(new ArrayList<>());
            directory.setLogTypeOverride(null);

            root.setData(directory);

            return repository.save(root);
        } else {
            throw new Exception("Root DirectoryModel already created. id: '" + list.get(0).getId() + "'");
        }
    }

    public void validateNotRoot(FileModel model) throws Exception {
        String name = model.getMetadata().getName();
        if (name.equals(ROOT_NAME)) {
            throw new Exception("Directory.metadata.name cannot be '" + ROOT_NAME + "'");
        }
    }

    public FileModel getRoot() throws Exception {
        List<FileModel> models = directoryGetService.findByName(ROOT_NAME);
        if (models.size() == 1) {
            return directoryGetService.findByName(ROOT_NAME).get(0);
        } else if (models.size() > 1) {
            // TODO log that they are multiple roots
            return directoryGetService.findByName(ROOT_NAME).get(0);
        } else {
            throw new Exception("Missing Root Directory");
        }
    }
}
