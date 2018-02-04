package com.loggerproject.directoryservice.server.controller.api;

import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "models", path = "directory")
public interface DirectoryRepositoryRestResource extends MongoRepository<DirectoryModel, String> {
    @RestResource(path = "name", rel = "name")
    List<DirectoryModel> findByName(@Param("name") String name);
}
