package com.loggerproject.coreservice.data.view;

import com.loggerproject.coreservice.data.view.model.ViewModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewModelRepository extends MongoRepository<ViewModel, String> {
}
