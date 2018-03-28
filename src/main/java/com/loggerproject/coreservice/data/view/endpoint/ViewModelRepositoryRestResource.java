package com.loggerproject.coreservice.data.view.endpoint;

import com.loggerproject.coreservice.data.view.model.ViewModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "models", path = "view")
public interface ViewModelRepositoryRestResource extends MongoRepository<ViewModel, String> {
}
