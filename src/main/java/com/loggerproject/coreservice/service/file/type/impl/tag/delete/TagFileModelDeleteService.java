package com.loggerproject.coreservice.service.file.type.impl.tag.delete;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.service.file.type.afiledata.delete.AFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.afiledata.delete.model.ModelBoundedToLogException;
import com.loggerproject.coreservice.service.file.type.impl.tag.create.TagFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.tag.get.TagFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.tag.update.TagFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TagFileModelDeleteService extends AFileModelDeleteService<TagFileData> {

    @Autowired
    public TagFileModelDeleteService(@Lazy TagFileModelCreateService globalServerCreateService,
                                     @Lazy TagFileModelDeleteService globalServerDeleteService,
                                     @Lazy TagFileModelGetService globalServerGetService,
                                     @Lazy TagFileModelUpdateService globalServerUpdateService) {
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
