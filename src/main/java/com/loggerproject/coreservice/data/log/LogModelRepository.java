package com.loggerproject.coreservice.data.log;

import com.loggerproject.coreservice.data.log.model.LogModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogModelRepository extends MongoRepository<LogModel, String> {
}
