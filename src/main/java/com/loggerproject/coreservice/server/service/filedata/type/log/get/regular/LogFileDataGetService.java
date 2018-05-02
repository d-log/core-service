package com.loggerproject.coreservice.server.service.filedata.type.log.get.regular;

import com.loggerproject.coreservice.server.data.document.file.FileModel;
import com.loggerproject.coreservice.server.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.server.data.document.file.extra.data.logdirectory.LogDirectoryFileData;
import com.loggerproject.coreservice.server.data.document.file.extra.data.tag.TagFileData;
import com.loggerproject.coreservice.server.service.filedata.afiledata.get.AFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.log.create.LogFileDataCreateService;
import com.loggerproject.coreservice.server.service.filedata.type.log.delete.LogFileDataDeleteService;
import com.loggerproject.coreservice.server.service.filedata.type.log.get.regular.extra.GetterRequest;
import com.loggerproject.coreservice.server.service.filedata.type.log.update.LogFileDataUpdateService;
import com.loggerproject.coreservice.server.service.filedata.type.logdirectory.get.LogDirectoryFileDataGetService;
import com.loggerproject.coreservice.server.service.filedata.type.tag.get.TagFileDataGetService;
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

import java.util.*;

@Service
public class LogFileDataGetService extends AFileDataGetService<LogFileData> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    LogDirectoryFileDataGetService directoryGetService;

    @Autowired
    TagFileDataGetService tagGetService;

    private Pageable defaultPageableLatest;

    @Autowired
    public LogFileDataGetService(@Lazy LogFileDataCreateService globalServerCreateService,
                                 @Lazy LogFileDataDeleteService globalServerDeleteService,
                                 @Lazy LogFileDataGetService globalServerGetService,
                                 @Lazy LogFileDataUpdateService globalServerUpdateService,
                                 @Value("${spring.data.rest.maxPageSize}") Integer maxPageSize) {
        super(globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
        defaultPageableLatest = new PageRequest(0, maxPageSize, new Sort(new Sort.Order(Sort.Direction.DESC, "metadata.created")));
    }

    public Page<FileModel> theGetter(GetterRequest getterRequest) {
        Long millisecondThreshold = getterRequest.getMillisecondThreshold();
        Pageable pageable = getterRequest.getPageable();

        // scrub parameters
        if (millisecondThreshold == null) {
            millisecondThreshold = System.currentTimeMillis();
        }
        if (pageable == null) {
            pageable = defaultPageableLatest;
        }
        if (pageable.getSort() == null) {
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), defaultPageableLatest.getSort());
        }

        // build query
        Query query = new Query();
        query.addCriteria(Criteria.where("metadata.type").is(genericClass.getSimpleName()));
        query.addCriteria(Criteria.where("metadata.created").lte(new Date(millisecondThreshold)));
        query.with(pageable);

        if (getterRequest.getSearchString() != null) {
            Set<String> logIds = getLogIDs(getterRequest.getSearchString());
            List<ObjectId> objectIds = new ArrayList<>();
            for (String logID : logIds) {
                objectIds.add(new ObjectId(logID));
            }
            query.addCriteria(Criteria.where("_id").in(objectIds));
        }

        if (getterRequest.getDirectoryID() != null) {
            query.addCriteria(Criteria.where("data.organization.parentLogDirectoryFileIDs").in(getterRequest.getDirectoryID()));
        }

        if (getterRequest.getTagID() != null) {
            query.addCriteria(Criteria.where("data.organization.tagFileIDs").in(getterRequest.getTagID()));
        }

        // execute and build page
        List<FileModel> list = mongoTemplate.find(query, FileModel.class);
        dataConvertValue(list);
        return PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(query, FileModel.class));
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
