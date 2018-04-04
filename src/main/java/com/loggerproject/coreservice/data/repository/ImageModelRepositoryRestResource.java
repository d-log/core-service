package com.loggerproject.coreservice.data.repository;

import com.loggerproject.coreservice.data.document.image.ImageModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "models", path = "/admin/image")
public interface ImageModelRepositoryRestResource extends MongoRepository<ImageModel, String> {
}
