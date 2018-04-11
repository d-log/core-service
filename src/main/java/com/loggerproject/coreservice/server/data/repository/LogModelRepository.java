package com.loggerproject.coreservice.server.data.repository;

import com.loggerproject.coreservice.server.data.document.log.LogModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogModelRepository extends MongoRepository<LogModel, String> {
}
