package com.loggerproject.coreservice.service.image.get;

import com.loggerproject.coreservice.data.model.image.ImageModel;
import com.loggerproject.coreservice.data.repository.ImageModelRepository;
import com.loggerproject.coreservice.service.aglobal.get.AGlobalModelGetService;
import com.loggerproject.coreservice.service.image.create.ImageModelCreateService;
import com.loggerproject.coreservice.service.image.delete.ImageModelDeleteService;
import com.loggerproject.coreservice.service.image.update.ImageModelUpdateService;
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
public class ImageModelGetService extends AGlobalModelGetService<ImageModel> {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    public ImageModelGetService(ImageModelRepository repository,
                                @Lazy ImageModelCreateService globalServerCreateService,
                                @Lazy ImageModelDeleteService globalServerDeleteService,
                                @Lazy ImageModelGetService globalServerGetService,
                                @Lazy ImageModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public Page<ImageModel> theGetter(ImageGetterRequest getterRequest) {
        scrubAndValidate(getterRequest);
        Query query = buildQuery(getterRequest);
        List<ImageModel> list = mongoTemplate.find(query, ImageModel.class);
        return PageableExecutionUtils.getPage(
                list,
                getterRequest.getPageable(),
                () -> mongoTemplate.count(query, ImageModel.class));
    }

    private void scrubAndValidate(ImageGetterRequest getterRequest) {
        if (getterRequest.getPageable() == null) {
            getterRequest.setPageable(defaultPageable);
        } else if (getterRequest.getPageable().getSort() == null) {
            Pageable oldPageable = getterRequest.getPageable();
            Pageable newPageable = new PageRequest(oldPageable.getPageNumber(), oldPageable.getPageSize(), defaultPageable.getSort());
            getterRequest.setPageable(newPageable);
        }
    }

    private Query buildQuery(ImageGetterRequest getterRequest) {
        Query query = new Query();

        if (getterRequest.getCreatedBefore() != null) {
            query.addCriteria(Criteria.where("metadata.created").lte(new Date(getterRequest.getCreatedBefore())));
        }
        if (getterRequest.getPageable() != null) {
            query.with(getterRequest.getPageable());
        }

        return query;
    }
}
