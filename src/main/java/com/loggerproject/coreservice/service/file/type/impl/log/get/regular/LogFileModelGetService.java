package com.loggerproject.coreservice.service.file.type.impl.log.get.regular;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.service.file.FileModelGetService;
import com.loggerproject.coreservice.service.file.extra.FileGetterRequest;
import com.loggerproject.coreservice.service.file.type.afiledata.get.AFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.log.create.LogFileModelCreateService;
import com.loggerproject.coreservice.service.file.type.impl.log.delete.LogFileModelDeleteService;
import com.loggerproject.coreservice.service.file.type.impl.log.update.LogFileModelUpdateService;
import com.loggerproject.coreservice.service.file.type.impl.logdirectory.get.regular.LogDirectoryFileModelGetService;
import com.loggerproject.coreservice.service.file.type.impl.tag.get.TagFileModelGetService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LogFileModelGetService extends AFileModelGetService<LogFileData> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    LogDirectoryFileModelGetService directoryGetService;

    @Autowired
    TagFileModelGetService tagGetService;

    @Autowired
    FileModelGetService fileModelGetService;

    @Autowired
    public LogFileModelGetService(@Lazy LogFileModelCreateService globalServerCreateService,
                                  @Lazy LogFileModelDeleteService globalServerDeleteService,
                                  @Lazy LogFileModelGetService globalServerGetService,
                                  @Lazy LogFileModelUpdateService globalServerUpdateService) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public Page<FileModel> theGetter(FileGetterRequest getterRequest) {
        scrubAndValidate(getterRequest);
        Query query = buildQuery(getterRequest);
        List<FileModel> list = mongoTemplate.find(query, FileModel.class);
        convertFileDataObject2Generic(list);
        return PageableExecutionUtils.getPage(
                list,
                getterRequest.getPageable(),
                () -> mongoTemplate.count(query, FileModel.class));
    }

    private void scrubAndValidate(FileGetterRequest getterRequest) {
        fileModelGetService.scrubAndValidate(getterRequest);
        getterRequest.setFileTypes(Collections.singleton(genericClass.getSimpleName()));
    }

    private Query buildQuery(FileGetterRequest getterRequest) {
        Query query = fileModelGetService.buildQuery(getterRequest);

        if (getterRequest.getSearchString() != null) {
            Set<String> logIds = getLogIDs(getterRequest.getSearchString());
            List<ObjectId> objectIds = new ArrayList<>();
            for (String logID : logIds) {
                objectIds.add(new ObjectId(logID));
            }
            query.addCriteria(Criteria.where("_id").in(objectIds));
        }

        return query;
    }

    private Set<String> getLogIDs(String name) {
        Set<String> logIDs = new HashSet<>();

        List<FileModel> dfs = directoryGetService.findByName(name);
        for (FileModel df : dfs) {
            LogDirectoryFileData d = (LogDirectoryFileData) df.getData();
            logIDs.addAll(d.getLogFileIDs());
        }

        List<FileModel> tfs = tagGetService.findByName(name);
        for (FileModel tf : tfs) {
            TagFileData t = (TagFileData) tf.getData();
            logIDs.addAll(t.getLogFileIDs());
        }

        return logIDs;
    }
}
