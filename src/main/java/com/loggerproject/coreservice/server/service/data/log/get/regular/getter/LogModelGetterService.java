package com.loggerproject.coreservice.server.service.data.log.get.regular.getter;

import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.data.document.tag.TagModel;
import com.loggerproject.coreservice.server.service.data.directory.get.DirectoryModelGetService;
import com.loggerproject.coreservice.server.service.data.log.get.regular.getter.model.GetterRequest;
import com.loggerproject.coreservice.server.service.data.tag.get.TagModelGetService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class LogModelGetterService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    DirectoryModelGetService directoryModelGetService;

    @Autowired
    TagModelGetService tagModelGetService;

    private Pageable defaultPageableLatest;

    public LogModelGetterService(@Value("${spring.data.rest.maxPageSize}") Integer maxPageSize) {
        defaultPageableLatest = new PageRequest(0, maxPageSize, new Sort(new Sort.Order(Sort.Direction.DESC, "metadata.created")));
    }

    public Page<LogModel> theGetter(GetterRequest getterRequest) {
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

        // execute and build page
        List<LogModel> list = mongoTemplate.find(query, LogModel.class);
        return PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(query, LogModel.class));
    }

    private Set<String> getLogIDs(String name) {
        Set<String> logIDs = new HashSet<>();

        List<DirectoryModel> directoryModels = directoryModelGetService.findByName(name);
        for (DirectoryModel directoryModel : directoryModels) {
            logIDs.addAll(directoryModel.getLogIDs());
        }

        List<TagModel> tagModels = tagModelGetService.findByName(name);
        for (TagModel tagModel : tagModels) {
            logIDs.addAll(tagModel.getLogIDs());
        }

        return logIDs;
    }
}
