package com.loggerproject.coreservice.service.file.type.impl.logdirectory.update;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.service.file.type.afiledata.update.AFileModelUpdateService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.create.LogDirectoryFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.delete.LogDirectoryFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.regular.LogDirectoryFileModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LogDirectoryFileModelUpdateService extends AFileModelUpdateService<LogDirectoryFileData> {

    @Autowired
    public LogDirectoryFileModelUpdateService(@Lazy LogDirectoryFileModelCreateService globalServerCreateService,
                                              @Lazy LogDirectoryFileModelDeleteService globalServerDeleteService,
                                              @Lazy LogDirectoryFileModelGetService globalServerGetService,
                                              @Lazy LogDirectoryFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public FileModel changeName(String id, String name) throws Exception {
        FileModel model = globalServerGetService.validateAndFindOne(id);
        model.getMetadata().setName(name);
        return this.update(model);
    }

    public FileModel assignFromParentToParent(String childID, String oldParentID, String newParentID) throws Exception {
        FileModel model = globalServerGetService.validateAndFindOne(childID);
        FileModel oldParent = globalServerGetService.validateAndFindOne(oldParentID);
        FileModel newParent = globalServerGetService.validateAndFindOne(newParentID);

        LogDirectoryFileData md = (LogDirectoryFileData) model.getData();
        LogDirectoryFileData od = (LogDirectoryFileData) oldParent.getData();
        LogDirectoryFileData nd = (LogDirectoryFileData) newParent.getData();

        md.getOrganization().getParentLogDirectoryFileIDs().remove(oldParentID);
        od.getChildLogDirectoryFileIDs().remove(childID);

        md.getOrganization().getParentLogDirectoryFileIDs().add(newParentID);
        nd.getChildLogDirectoryFileIDs().add(childID);

        update(oldParent);
        update(newParent);

        return update(model);
    }

    public FileModel assignAdditionalParent(String childID, String parentID) throws Exception {
        FileModel model = globalServerGetService.validateAndFindOne(childID);
        FileModel parent = globalServerGetService.validateAndFindOne(parentID);

        LogDirectoryFileData md = (LogDirectoryFileData) model.getData();
        LogDirectoryFileData pd = (LogDirectoryFileData) parent.getData();

        md.getOrganization().getParentLogDirectoryFileIDs().add(parentID);
        pd.getChildLogDirectoryFileIDs().add(childID);

        update(parent);

        return update(model);
    }
}
