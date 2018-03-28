package com.loggerproject.coreservice.data.viewtemplatetheme.endpoint;

import com.loggerproject.coreservice.data.viewtemplatetheme.model.ViewTemplateThemeModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "models", path = "view-template-theme")
public interface ViewTemplateThemeModelRepositoryRestResource extends MongoRepository<ViewTemplateThemeModel, String> {
}
