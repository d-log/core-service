package com.loggerproject.coreservice.service.file.type.impl.tag.get;

import com.loggerproject.coreservice.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.service.file.type.afiledata.get.AFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.tag.create.TagFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.tag.delete.TagFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.tag.update.TagFileModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class TagFileModelGetService extends AFileModelGetService<TagFileData> {

    @Autowired
    public TagFileModelGetService(@Lazy TagFileModelCreateService globalServerCreateService,
                                  @Lazy TagFileModelDeleteService globalServerDeleteService,
                                  @Lazy TagFileModelGetService globalServerGetService,
                                  @Lazy TagFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }
}
