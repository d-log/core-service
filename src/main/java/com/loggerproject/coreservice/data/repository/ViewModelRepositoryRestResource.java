package com.loggerproject.coreservice.data.repository;

import com.loggerproject.coreservice.data.model.view.ViewModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "models", path = "/admin/api/view")
public interface ViewModelRepositoryRestResource extends MongoRepository<ViewModel, String> {
}
