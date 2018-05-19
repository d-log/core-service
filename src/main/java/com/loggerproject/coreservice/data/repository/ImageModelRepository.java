package com.loggerproject.coreservice.data.repository;

import com.loggerproject.coreservice.data.model.image.ImageModel;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageModelRepository extends GlobalModelRepository<ImageModel, String> {
}
