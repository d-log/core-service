package com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.regular;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.service.file.type.afiledata.get.AFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.RootLogDirectoryService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.create.LogDirectoryFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.delete.LogDirectoryFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.update.LogDirectoryFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class LogDirectoryFileModelGetService extends AFileModelGetService<LogDirectoryFileData> {

    @Autowired
    RootLogDirectoryService rootLogDirectoryService;

    @Autowired
    public LogDirectoryFileModelGetService(@Lazy LogDirectoryFileModelCreateService globalServerCreateService,
                                           @Lazy LogDirectoryFileModelDeleteService globalServerDeleteService,
                                           @Lazy LogDirectoryFileModelGetService globalServerGetService,
                                           @Lazy LogDirectoryFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public FileModel getRoot() throws Exception {
        return rootLogDirectoryService.getRoot();
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
                if (child != null) {
                    children.add(child);
                }
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
        FileModel model = validateAndFindOne(id);
        LogDirectoryFileData directory = (LogDirectoryFileData) model.getData();
        Set<String> parentIDs = directory.getOrganization().getParentLogDirectoryFileIDs();
        return findByIds(parentIDs);
    }

    // TODO
//    public List<FileModel> getAncestryUpToRoot(String id) throws Exception {
//        FileModel model = validateAndFindOne(id);
//        LogDirectoryFileData directory = (LogDirectoryFileData)model.getData();
//        if (!directory.getOrganization().getParentLogDirectoryFileIDs().isEmpty()) {
//
//        }
//    }
}
