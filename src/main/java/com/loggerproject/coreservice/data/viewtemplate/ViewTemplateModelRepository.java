package com.loggerproject.coreservice.data.viewtemplate;

import com.loggerproject.coreservice.data.viewtemplate.model.ViewTemplateModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewTemplateModelRepository extends MongoRepository<ViewTemplateModel, String> {
}
