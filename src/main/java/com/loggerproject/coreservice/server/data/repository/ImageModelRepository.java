package com.loggerproject.coreservice.server.data.repository;

import com.loggerproject.coreservice.server.data.document.image.ImageModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageModelRepository extends MongoRepository<ImageModel, String> {
}
