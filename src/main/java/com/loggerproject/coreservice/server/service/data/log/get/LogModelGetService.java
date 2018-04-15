package com.loggerproject.coreservice.server.service.data.log.get;

import com.loggerproject.coreservice.global.server.service.get.GlobalServerGetService;
import com.loggerproject.coreservice.server.data.document.log.LogModel;
import com.loggerproject.coreservice.server.data.repository.LogModelRepository;
import com.loggerproject.coreservice.server.service.data.log.create.LogModelCreateService;
import com.loggerproject.coreservice.server.service.data.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.server.service.data.log.update.LogModelUpdateService;
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

import java.util.Date;
import java.util.List;

@Service
public class LogModelGetService extends GlobalServerGetService<LogModel> {

    @Autowired
    LogModelRepository logModelRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    private Pageable defaultPageableLatest;

    @Autowired
    public LogModelGetService(LogModelRepository repository,
                              @Lazy LogModelCreateService globalServerCreateService,
                              @Lazy LogModelDeleteService globalServerDeleteService,
                              @Lazy LogModelGetService globalServerGetService,
                              @Lazy LogModelUpdateService globalServerUpdateService,
                              @Value("${spring.data.rest.maxPageSize}") Integer maxPageSize) {
        super(repository,
                globalServerCreateService,
                globalServerDeleteService,
                globalServerGetService,
                globalServerUpdateService,
                maxPageSize);
        defaultPageableLatest = new PageRequest(0, maxPageSize, new Sort(new Sort.Order(Sort.Direction.DESC, "metadata.created")));
    }

    public Page<LogModel> theGetter(Date createdDateThreshold, Pageable pageable) throws Exception {
        // scrub parameters
        if (createdDateThreshold == null) {
            createdDateThreshold = new Date();
        }
        if (pageable == null) {
            pageable = defaultPageable;
        }
        if (pageable.getSort() == null) {
            pageable = new PageRequest(pageable.getPageNumber(), pageable.getPageSize(), defaultPageableLatest.getSort());
        }
        pageable = scrubValidatePageable(pageable);

        // build query
        Query query = new Query();
        query.addCriteria(Criteria.where("metadata.created").lte(createdDateThreshold));
        query.with(pageable);

        // execute and build page
        List<LogModel> list = mongoTemplate.find(query, LogModel.class);
        return PageableExecutionUtils.getPage(
                list,
                pageable,
                () -> mongoTemplate.count(query, LogModel.class));
    }
}
