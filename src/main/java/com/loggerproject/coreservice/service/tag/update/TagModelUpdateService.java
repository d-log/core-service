package com.loggerproject.coreservice.service.tag.update;

import com.loggerproject.coreservice.data.model.tag.TagModel;
import com.loggerproject.coreservice.data.repository.TagModelRepository;
import com.loggerproject.coreservice.service.aglobal.update.AGlobalModelUpdateService;
import com.loggerproject.coreservice.service.tag.create.TagModelCreateService;
import com.loggerproject.coreservice.service.tag.delete.TagModelDeleteService;
import com.loggerproject.coreservice.service.tag.get.TagModelGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagModelUpdateService extends AGlobalModelUpdateService<TagModel> {

    @Autowired
    TagModelGetService tagModelGetService;

    @Autowired
    public TagModelUpdateService(TagModelRepository repository,
                                 @Lazy TagModelCreateService globalServerCreateService,
                                 @Lazy TagModelDeleteService globalServerDeleteService,
                                 @Lazy TagModelGetService globalServerGetService,
                                 @Lazy TagModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public TagModel updateWholeModelAndSyncOtherDocuments(TagModel model) throws Exception {
        TagModel old = changeName(model.getId(), model.getMetadata().getName());
        old.getMetadata().setDescription(model.getMetadata().getDescription());
        return update(old);
    }

    public TagModel changeName(String id, String name) throws Exception {
        TagModel model = tagModelGetService.validateAndFindOne(id);

        if (!model.getMetadata().getName().equals(name)) {
            List<TagModel> list = tagModelGetService.findByName(name);
            if (list.size() > 0) {
                throw new Exception("TagModel.metadata.name: '" + name + "' already exists: '" + list.get(0).getId() + "'");
            }

            model.getMetadata().setName(name);
            model = update(model);
        }

        return model;
    }
}
