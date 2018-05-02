package com.loggerproject.coreservice.server.service.filedata.type.log.get.regular;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.server.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.server.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.server.service.file.FileModelGetService;
import com.loggerproject.coreservice.server.service.file.extra.FileGetterRequest;
import com.loggerproject.coreservice.server.service.filedata.afiledata.get.AFileModelGetService;
import com.loggerproject.coreservice.server.service.filedata.type.log.create.LogFileModelCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.log.delete.LogFileModelDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.log.update.LogFileModelUpdateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileModelGetService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.get.TagFileModelGetService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    private Pageable defaultPageableLatest;

    @Autowired
    public LogFileModelGetService(@Lazy LogFileModelCreateService globalServerCreateService,
                                  @Lazy LogFileModelDeleteService globalServerDeleteService,
                                  @Lazy LogFileModelGetService globalServerGetService,
                                  @Lazy LogFileModelUpdateService globalServerUpdateService,
                                  @Value("${spring.data.rest.maxPageSize}") Integer maxPageSize) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
        defaultPageableLatest = new PageRequest(0, maxPageSize, new Sort(new Sort.Order(Sort.Direction.DESC, "metadata.created")));
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
        getterRequest.setFileType(genericClass.getSimpleName());
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
