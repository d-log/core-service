package com.loggerproject.coreservice.server.data.repository;

import com.loggerproject.coreservice.server.data.document.view.ViewModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ViewModelRepository extends MongoRepository<ViewModel, String> {
    List<ViewModel> findByName(@Param("name") String name);
}
