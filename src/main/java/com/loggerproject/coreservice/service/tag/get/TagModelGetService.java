package com.loggerproject.coreservice.service.tag.get;

import com.loggerproject.coreservice.data.model.tag.TagModel;
import com.loggerproject.coreservice.data.repository.TagModelRepository;
import com.loggerproject.coreservice.service.aglobal.get.AGlobalModelGetService;
import com.loggerproject.coreservice.service.tag.create.TagModelCreateService;
import com.loggerproject.coreservice.service.tag.delete.TagModelDeleteService;
import com.loggerproject.coreservice.service.tag.update.TagModelUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class TagModelGetService extends AGlobalModelGetService<TagModel> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    public TagModelGetService(TagModelRepository repository,
                              @Lazy TagModelCreateService globalServerCreateService,
                              @Lazy TagModelDeleteService globalServerDeleteService,
                              @Lazy TagModelGetService globalServerGetService,
                              @Lazy TagModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public Page<TagModel> theGetter(TagGetterRequest getterRequest) {
        scrubAndValidate(getterRequest);
        Query query = buildQuery(getterRequest);
        List<TagModel> list = mongoTemplate.find(query, TagModel.class);
        return PageableExecutionUtils.getPage(
                list,
                getterRequest.getPageable(),
                () -> mongoTemplate.count(query, TagModel.class));
    }

    private void scrubAndValidate(TagGetterRequest getterRequest) {
        if (getterRequest.getPageable() == null) {
            getterRequest.setPageable(defaultPageable);
        } else if (getterRequest.getPageable().getSort() == null) {
            Pageable oldPageable = getterRequest.getPageable();
            Pageable newPageable = new PageRequest(oldPageable.getPageNumber(), oldPageable.getPageSize(), defaultPageable.getSort());
            getterRequest.setPageable(newPageable);
        }
    }

    private Query buildQuery(TagGetterRequest getterRequest) {
        Query query = new Query();

        if (getterRequest.getMetadataNameLike() != null) {
            query.addCriteria(Criteria.where("metadata.name").regex(getterRequest.getMetadataNameLike(), "i"));
        }
        if (getterRequest.getCreatedBefore() != null) {
            query.addCriteria(Criteria.where("metadata.created").lte(new Date(getterRequest.getCreatedBefore())));
        }
        if (getterRequest.getPageable() != null) {
            query.with(getterRequest.getPageable());
        }

        return query;
    }
}
