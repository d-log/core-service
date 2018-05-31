package com.loggerproject.coreservice.service.log.get.regular;

import com.google.common.collect.Lists;
import com.loggerproject.coreservice.data.model.log.LogModel;
import com.loggerproject.coreservice.data.repository.LogModelRepository;
import com.loggerproject.coreservice.service.aglobal.get.AGlobalModelGetService;
import com.loggerproject.coreservice.service.log.RootLogModelService;
import com.loggerproject.coreservice.service.log.create.LogModelCreateService;
import com.loggerproject.coreservice.service.log.delete.LogModelDeleteService;
import com.loggerproject.coreservice.service.log.get.regular.extra.LogGetterRequest;
import com.loggerproject.coreservice.service.log.update.LogModelUpdateService;
import org.apache.commons.collections.CollectionUtils;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class LogModelGetService extends AGlobalModelGetService<LogModel> {

    @Autowired
    RootLogModelService rootLogModelService;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    public LogModelGetService(LogModelRepository repository,
                              @Lazy LogModelCreateService globalServerCreateService,
                              @Lazy LogModelDeleteService globalServerDeleteService,
                              @Lazy LogModelGetService globalServerGetService,
                              @Lazy LogModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCreateService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public Page<LogModel> theGetter(LogGetterRequest getterRequest) {
        scrubAndValidate(getterRequest);
        Query query = buildQuery(getterRequest);
        List<LogModel> list = mongoTemplate.find(query, LogModel.class);

        // LogDisplayType transform is done at logDisplayTypeModelGetManagerService.theGetter() level
//        List<ALogDisplayType> typedList = logDisplayTypeModelGetManagerService.getAsLogDisplayType(list, getterRequest.getLogDisplayType());
        return PageableExecutionUtils.getPage(
                list,
                getterRequest.getPageable(),
                () -> mongoTemplate.count(query, LogModel.class));
    }

    private void scrubAndValidate(LogGetterRequest getterRequest) {
        if (getterRequest.getPageable() == null) {
            getterRequest.setPageable(defaultPageable);
        } else if (getterRequest.getPageable().getSort() == null) {
            Pageable oldPageable = getterRequest.getPageable();
            Pageable newPageable = new PageRequest(oldPageable.getPageNumber(), oldPageable.getPageSize(), defaultPageable.getSort());
            getterRequest.setPageable(newPageable);
        }
    }

    private Query buildQuery(LogGetterRequest getterRequest) {
        Query query = new Query();

        if (getterRequest.getSearchString() != null) {
            // TODO something here
        }
        if (getterRequest.getMetadataNameLike() != null) {
            query.addCriteria(Criteria.where("metadata.name").regex(getterRequest.getMetadataNameLike(), "i"));
        }
        if (getterRequest.getTagID() != null) {
            query.addCriteria(Criteria.where("logOrganization.tagIDs").in(getterRequest.getTagID()));
        }
        if (getterRequest.getParentLogID() != null) {
            query.addCriteria(Criteria.where("logOrganization.parentLogIDs").in(getterRequest.getParentLogID()));
        }
        if (getterRequest.getCreatedBefore() != null) {
            query.addCriteria(Criteria.where("metadata.created").lte(new Date(getterRequest.getCreatedBefore())));
        }
        if (getterRequest.getPageable() != null) {
            query.with(getterRequest.getPageable());
        }

        return query;
    }


    public LogModel getRoot() throws Exception {
        return rootLogModelService.getRoot();
    }

    /**
     * @param id
     * @param level - if null then default 1
     * @return FileModel[]
     * @throws Exception
     */
    public List<LogModel> findChildren(String id, Integer level) throws Exception {
        level = level == null ? 1 : level;
        List<LogModel> children = new ArrayList<>();

        if (level > 0) {
            LogModel directory = validateAndFindOne(id);
            Set<String> childrenIDs = directory.getLogOrganization().getChildLogIDs();

            for (String childID : childrenIDs) {
                LogModel child = findOne(childID);
                if (child != null) {
                    children.add(child);
                }
            }

            List<LogModel> grandChildren = new ArrayList<>();
            for (LogModel child : children) {
                grandChildren.addAll(findChildren(child.getId(), level - 1));
            }
            children.addAll(grandChildren);
        }

        return children;
    }

    public LogModel findParent(String id) throws Exception {
        LogModel model = validateAndFindOne(id);
        Set<String> parentLogIDs = model.getLogOrganization().getParentLogIDs();
        if (CollectionUtils.isNotEmpty(parentLogIDs)) {
            return findOne(parentLogIDs.iterator().next());
        } else {
            return null;
        }
    }

    /**
     * Returns a List of LogModels from root to direct parent
     * @param id
     * @return
     * @throws Exception
     */
    public List<LogModel> getAncestryLogModels(String id) throws Exception {
        List<LogModel> ancestryLogModels = new ArrayList<>();

        LogModel currentParent = findParent(id);
        while (currentParent != null) {
            ancestryLogModels.add(currentParent);
            currentParent = findParent(currentParent.getId());
        }

        return Lists.reverse(ancestryLogModels);
    }
}
