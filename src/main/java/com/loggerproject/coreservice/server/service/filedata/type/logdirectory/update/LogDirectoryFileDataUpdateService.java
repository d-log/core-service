package com.loggerproject.coreservice.server.service.filedata.type.logdirectory.update;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.update.AFileDataUpdateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.create.LogDirectoryFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.delete.LogDirectoryFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileDataGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class LogDirectoryFileDataUpdateService extends AFileDataUpdateService<LogDirectoryFileData> {

    @Autowired
    public LogDirectoryFileDataUpdateService(@Lazy LogDirectoryFileDataCreateService globalServerCreateService,
                                    @Lazy LogDirectoryFileDataDeleteService globalServerDeleteService,
                                    @Lazy LogDirectoryFileDataGetService globalServerGetService,
                                    @Lazy LogDirectoryFileDataUpdateService globalServerUpdateService) {
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
