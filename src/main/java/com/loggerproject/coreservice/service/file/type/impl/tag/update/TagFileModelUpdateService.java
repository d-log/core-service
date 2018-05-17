package com.loggerproject.coreservice.service.file.type.impl.tag.update;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.service.file.type.afiledata.update.AFileModelUpdateService;
import com.loggerproject.coreservice.service.file.type.impl.tag.create.TagFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.tag.delete.TagFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.tag.get.TagFileModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public FileModel updateWholeModelAndSyncOtherDocuments(FileModel model) throws Exception {
        FileModel old = changeName(model.getId(), model.getMetadata().getName());
        old.getMetadata().setDescription(model.getMetadata().getDescription());
        return update(old);
    }

    public FileModel changeName(String id, String name) throws Exception {
        FileModel model = tagFileDataGetService.validateAndFindOne(id);

        if (!model.getMetadata().getName().equals(name)) {
            List<FileModel> list = tagFileDataGetService.findByName(name);
            if (list.size() > 0) {
                throw new Exception("TagFileData.metadata.name: '" + name + "' already exists: '" + list.get(0).getId() + "'");
            }

            model.getMetadata().setName(name);
            model = update(model);
        }

        return model;
    }
}
