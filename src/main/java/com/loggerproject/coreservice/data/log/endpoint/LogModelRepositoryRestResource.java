package com.loggerproject.coreservice.data.log.endpoint;

import com.loggerproject.coreservice.data.log.model.LogModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "models", path = "log")
public interface LogModelRepositoryRestResource extends MongoRepository<LogModel, String> {
}
