package com.loggerproject.coreservice.data.directory.endpoint;

import com.loggerproject.coreservice.data.directory.model.DirectoryModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "models", path = "directory")
public interface DirectoryModelRepositoryRestResource extends MongoRepository<DirectoryModel, String> {
    @RestResource(path = "name", rel = "name")
    List<DirectoryModel> findByName(@Param("name") String name);
}
