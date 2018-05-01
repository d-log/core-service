package com.loggerproject.coreservice.server.service.filedata.type.tag.create;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.create.AFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.delete.TagFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.get.TagFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.update.TagFileDataUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;

@Service
public class TagFileDataCreateService extends AFileDataCreateService<TagFileData> {

    @Autowired
    TagFileDataGetService tagModelGetService;

    @Autowired
    public TagFileDataCreateService(@Lazy TagFileDataCreateService globalServerCreateService,
                                    @Lazy TagFileDataDeleteService globalServerDeleteService,
                                    @Lazy TagFileDataGetService globalServerGetService,
                                    @Lazy TagFileDataUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeSaveScrubAndValidate(FileModel model) throws Exception {
        TagFileData fileData = (TagFileData) model.getData();

        Assert.isTrue(CollectionUtils.isEmpty(fileData.getLogFileIDs()), "TagFileData.logFileIDs must be empty");
        fileData.setLogFileIDs(new HashSet<>());

        String name = model.getMetadata().getName();
        Assert.hasText(name, "TagFileData.metadata.name must not be empty");
        if (tagModelGetService.findByName(name).size() > 0) {
            throw new Exception("TagFileData.metadata.name: '" + name + "' already exists");
        }

        return model;
    }
}
