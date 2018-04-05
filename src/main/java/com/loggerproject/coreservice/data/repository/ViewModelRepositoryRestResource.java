package com.loggerproject.coreservice.data.repository;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "models", path = "/admin/api/view")
public interface ViewModelRepositoryRestResource extends MongoRepository<ViewModel, String> {
    @RestResource(path = "name", rel = "name")
    List<ViewModel> findByName(@Param("name") String name);
}
