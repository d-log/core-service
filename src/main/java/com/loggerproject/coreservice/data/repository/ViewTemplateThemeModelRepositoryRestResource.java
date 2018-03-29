package com.loggerproject.coreservice.data.repository;

import com.loggerproject.coreservice.data.model.viewtemplatetheme.ViewTemplateThemeModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "models", path = "/admin/api/view-template-theme")
public interface ViewTemplateThemeModelRepositoryRestResource extends MongoRepository<ViewTemplateThemeModel, String> {
}
