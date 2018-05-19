package com.loggerproject.coreservice.data.repository;

import com.loggerproject.coreservice.data.model.image.ImageModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageModelRepository extends GlobalModelRepository<ImageModel, String> {
}
