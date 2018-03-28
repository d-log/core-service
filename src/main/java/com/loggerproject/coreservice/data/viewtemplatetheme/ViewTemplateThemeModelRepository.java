package com.loggerproject.coreservice.data.viewtemplatetheme;

import com.loggerproject.coreservice.data.viewtemplatetheme.model.ViewTemplateThemeModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViewTemplateThemeModelRepository extends MongoRepository<ViewTemplateThemeModel, String> {
}
