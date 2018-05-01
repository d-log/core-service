package com.loggerproject.coreservice.server.service.filedata.type.tag.update;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.update.AFileDataUpdateService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.create.TagFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.delete.TagFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.get.TagFileDataGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TagFileDataUpdateService extends AFileDataUpdateService<TagFileData> {

    @Autowired
    TagFileDataGetService tagFileDataGetService;

    @Autowired
    public TagFileDataUpdateService(@Lazy TagFileDataCreateService globalServerCreateService,
                                    @Lazy TagFileDataDeleteService globalServerDeleteService,
                                    @Lazy TagFileDataGetService globalServerGetService,
                                    @Lazy TagFileDataUpdateService globalServerUpdateService) {
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
