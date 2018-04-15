package com.loggerproject.coreservice.server.data.repository;

import com.loggerproject.coreservice.server.data.document.log.LogModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface LogModelRepository extends MongoRepository<LogModel, String> {
    Page<LogModel> findByMetadata_CreatedLessThanEqualOrderByMetadata_CreatedDesc(Date date, Pageable pageable);
}
