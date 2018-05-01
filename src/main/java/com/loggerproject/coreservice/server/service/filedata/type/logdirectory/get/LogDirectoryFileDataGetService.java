package com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.server.data.repository.FileModelRepository;
import com.loggerproject.coreservice.server.service.filedata.afiledata.get.AFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.RootLogDirectoryService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.create.LogDirectoryFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.delete.LogDirectoryFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.update.LogDirectoryFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class LogDirectoryFileDataGetService extends AFileDataGetService<LogDirectoryFileData> {

    @Autowired
    FileModelRepository fileModelRepository;

    @Autowired
    RootLogDirectoryService rootLogDirectoryService;

    @Autowired
    public LogDirectoryFileDataGetService(@Lazy LogDirectoryFileDataCreateService globalServerCreateService,
                                 @Lazy LogDirectoryFileDataDeleteService globalServerDeleteService,
                                 @Lazy LogDirectoryFileDataGetService globalServerGetService,
                                 @Lazy LogDirectoryFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public FileModel getRoot() throws Exception {
        return rootLogDirectoryService.getRoot();
    }

    public List<FileModel> findByName(String name) {
        return fileModelRepository.findByMetadata_typeAndMetadata_name(genericClass.getSimpleName(), name);
    }

    public List<FileModel> findChildren(String id) throws Exception {
        return findChildren(id, 1);
    }

    /**
     * @param id
     * @param level - if null then default 1
     * @return FileModel[]
     * @throws Exception
     */
    public List<FileModel> findChildren(String id, Integer level) throws Exception {
        level = level == null ? 1 : level;
        List<FileModel> children = new ArrayList<>();

        if (level > 0) {
            FileModel model = this.validateAndFindOne(id);
            LogDirectoryFileData directory = (LogDirectoryFileData) model.getData();
            Set<String> childrenIDs = directory.getChildLogDirectoryFileIDs();

            for (String childID : childrenIDs) {
                FileModel child = findOne(childID);
                children.add(child);
            }

            List<FileModel> grandChildren = new ArrayList<>();
            for (FileModel child : children) {
                grandChildren.addAll(findChildren(child.getId(), level - 1));
            }
            children.addAll(grandChildren);
        }

        return children;
    }

    public List<FileModel> findParents(String id) throws Exception {
        FileModel model = this.validateAndFindOne(id);
        LogDirectoryFileData directory = (LogDirectoryFileData) model.getData();
        Set<String> parentIDs = directory.getOrganization().getParentLogDirectoryFileIDs();
        return findByIds(parentIDs);
    }
}
