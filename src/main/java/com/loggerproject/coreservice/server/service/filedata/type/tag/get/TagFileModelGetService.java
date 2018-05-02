package com.loggerproject.coreservice.server.service.filedata.type.tag.get;

import com.loggerproject.coreservice.server.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.get.AFileModelGetService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.create.TagFileModelCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.delete.TagFileModelDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.update.TagFileModelUpdateService;
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
