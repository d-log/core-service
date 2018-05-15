package com.loggerproject.coreservice.service.file.type.impl.tag.create;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.service.file.type.afiledata.create.AFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.tag.delete.TagFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.tag.get.TagFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.tag.update.TagFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;

@Service
public class TagFileModelCreateService extends AFileModelCreateService<TagFileData> {

    @Autowired
    TagFileModelGetService tagModelGetService;

    @Autowired
    public TagFileModelCreateService(@Lazy TagFileModelCreateService globalServerCreateService,
                                     @Lazy TagFileModelDeleteService globalServerDeleteService,
                                     @Lazy TagFileModelGetService globalServerGetService,
                                     @Lazy TagFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    @Override
    public FileModel beforeSaveScrubAndValidate(FileModel model) throws Exception {
        TagFileData fileData = (TagFileData) model.getData();

        Assert.isTrue(CollectionUtils.isEmpty(fileData.getLogFileIDs()), "TagFileData.logFileIDs must be empty");
        fileData.setLogFileIDs(new HashSet<>());

        model.getMetadata().setName(scrubAndValidateMetadataName(model));

        return model;
    }

    private String scrubAndValidateMetadataName(FileModel model) throws Exception {
        String name = model.getMetadata().getName();
        if (name.trim().length() != name.length()) {
            throw new Exception("TagFileData.metadata.name must not have leading and/or trailing whitespaces");
        }
        Assert.hasText(name, "TagFileData.metadata.name must not be empty");
        List<FileModel> list = tagModelGetService.findByName(name);
        if (list.size() > 0) {
            throw new Exception("TagFileData.metadata.name: '" + name + "' already exists: '" + list.get(0).getId() + "'");
        }
        return name;
    }
}
