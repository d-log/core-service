package com.loggerproject.coreservice.data.repository;

import com.loggerproject.coreservice.data.model.viewtemplate.ViewTemplateModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "models", path = "/admin/api/view-template")
public interface ViewTemplateModelRepositoryRestResource extends MongoRepository<ViewTemplateModel, String> {
}
