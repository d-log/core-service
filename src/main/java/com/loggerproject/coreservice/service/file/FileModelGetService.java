package com.loggerproject.coreservice.service.file;

import com.loggerproject.coreservice.data.document.file.FileModel;
import com.loggerproject.coreservice.data.document.file.extra.data.log.LogFileData;
import com.loggerproject.coreservice.service.file.extra.FileGetterRequest;
import com.loggerproject.coreservice.service.file.type.impl.log.get.LogType;
import com.loggerproject.coreservice.service.file.type.impl.log.get.TypedLogFileModelGetManagerService;
import org.apache.commons.collections.CollectionUtils;
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
public class FileModelGetService {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    TypedLogFileModelGetManagerService typedLogFileModelGetManagerService;

    private Pageable defaultPageableLatest;

    @Autowired
    public FileModelGetService(@Value("${spring.data.rest.maxPageSize}") Integer maxPageSize) {
        defaultPageableLatest = new PageRequest(0, maxPageSize, new Sort(new Sort.Order(Sort.Direction.DESC, "metadata.created")));
    }

    public Page<FileModel> theGetter(FileGetterRequest getterRequest) {
        scrubAndValidate(getterRequest);
        Query query = buildQuery(getterRequest);
        List<FileModel> list = mongoTemplate.find(query, FileModel.class);
        scrubAndValidate(getterRequest, list);
        return PageableExecutionUtils.getPage(
                list,
                getterRequest.getPageable(),
                () -> mongoTemplate.count(query, FileModel.class));
    }

    public void scrubAndValidate(FileGetterRequest getterRequest, List<FileModel> list) {
        LogType logType = getterRequest.getLogType();
        if (!(logType == null || logType == LogType.DEFAULT)) {
            for (int i = 0; i < list.size(); i++) {
                FileModel model = list.get(i);
                String fileType = model.getMetadata().getType();
                if (fileType.equals(LogFileData.class.getSimpleName())) {
                    list.set(i, typedLogFileModelGetManagerService.getAsLogType(model, logType));
                }
            }
        }
        // TODO add support for LogDirectoryType
    }

    public void scrubAndValidate(FileGetterRequest getterRequest) {
        if (getterRequest.getMillisecondThreshold() == null) {
            getterRequest.setMillisecondThreshold(System.currentTimeMillis());
        }

        if (getterRequest.getPageable() == null) {
            getterRequest.setPageable(defaultPageableLatest);
        } else if (getterRequest.getPageable().getSort() == null) {
            Pageable oldPageable = getterRequest.getPageable();
            Pageable newPageable = new PageRequest(oldPageable.getPageNumber(), oldPageable.getPageSize(), defaultPageableLatest.getSort());
            getterRequest.setPageable(newPageable);
        }
    }

    public Query buildQuery(FileGetterRequest getterRequest) {
        Query query = new Query();

        if (CollectionUtils.isNotEmpty(getterRequest.getFileTypes())) {
            query.addCriteria(Criteria.where("metadata.type").in(getterRequest.getFileTypes()));
        }
        if (getterRequest.getMetadataNameRegex() != null) {
            query.addCriteria(Criteria.where("metadata.name").regex(getterRequest.getMetadataNameRegex(), "i"));
        }
        if (getterRequest.getMillisecondThreshold() != null) {
            query.addCriteria(Criteria.where("metadata.created").lte(new Date(getterRequest.getMillisecondThreshold())));
        }
        if (getterRequest.getDirectoryID() != null) {
            query.addCriteria(Criteria.where("data.organization.parentLogDirectoryFileIDs").in(getterRequest.getDirectoryID()));
        }
        if (getterRequest.getTagID() != null) {
            query.addCriteria(Criteria.where("data.organization.tagFileIDs").in(getterRequest.getTagID()));
        }
        if (getterRequest.getPageable() != null) {
            query.with(getterRequest.getPageable());
        }

        return query;
    }
}
