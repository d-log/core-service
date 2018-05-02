package com.loggerproject.coreservice.server.service.filedata.type.tag.update;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.update.AFileModelUpdateService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.create.TagFileModelCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.delete.TagFileModelDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.get.TagFileModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TagFileModelUpdateService extends AFileModelUpdateService<TagFileData> {

    @Autowired
    TagFileModelGetService tagFileDataGetService;

    @Autowired
    public TagFileModelUpdateService(@Lazy TagFileModelCreateService globalServerCreateService,
                                     @Lazy TagFileModelDeleteService globalServerDeleteService,
                                     @Lazy TagFileModelGetService globalServerGetService,
                                     @Lazy TagFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public FileModel changeName(String id, String name) throws Exception {
        FileModel model = tagFileDataGetService.validateAndFindOne(id);

        if (tagFileDataGetService.findByName(name).size() > 0) {
            throw new Exception("TagFileData.metadata.name: '" + name + "' already exists");
        }

        model.getMetadata().setName(name);
        return this.update(model);
    }
}
