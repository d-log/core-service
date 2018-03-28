package com.loggerproject.coreservice.data.viewtemplate.endpoint;

import com.loggerproject.coreservice.data.viewtemplate.model.ViewTemplateModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "models", path = "view-template")
public interface ViewTemplateModelRepositoryRestResource extends MongoRepository<ViewTemplateModel, String> {
}
