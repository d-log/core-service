package com.loggerproject.coreservice.server.service.filedata.type.tag.delete;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.AFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.afiledata.delete.model.ModelBoundedToLogException;
import com.loggerproject.coreservice.server.service.filedata.type.tag.create.TagFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.get.TagFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.update.TagFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TagFileDataDeleteService extends AFileDataDeleteService<TagFileData> {

    @Autowired
    public TagFileDataDeleteService(@Lazy TagFileDataCreateService globalServerCreateService,
                                    @Lazy TagFileDataDeleteService globalServerDeleteService,
                                    @Lazy TagFileDataGetService globalServerGetService,
                                    @Lazy TagFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeDeleteValidate(FileModel model) throws Exception {
        TagFileData fileData = (TagFileData) model.getData();
        if (fileData.getLogFileIDs().size() > 0) {
            throw new ModelBoundedToLogException(model.getId(), fileData.getLogFileIDs());
        }
        return model;
    }
}
