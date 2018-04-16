package com.loggerproject.coreservice.server.service.data.log.get.regular.getter;

import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.service.data.log.get.regular.getter.model.GetterRequest;
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

import java.util.Date;
import java.util.List;

@Service
public class LogModelGetterService {

    @Autowired
    MongoTemplate mongoTemplate;

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

        // execute and build page
        List<LogModel> list = mongoTemplate.find(query, LogModel.class);
        return PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(query, LogModel.class));
    }
}
