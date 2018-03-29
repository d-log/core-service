package com.loggerproject.coreservice.data.repository;

import com.loggerproject.coreservice.data.model.log.LogModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "models", path = "/admin/api/log")
public interface LogModelRepositoryRestResource extends MongoRepository<LogModel, String> {
}
