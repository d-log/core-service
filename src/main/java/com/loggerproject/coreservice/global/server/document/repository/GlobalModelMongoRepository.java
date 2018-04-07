package com.loggerproject.coreservice.global.server.document.repository;

import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.query.MongoEntityInformation;
import org.springframework.data.mongodb.repository.support.SimpleMongoRepository;
import org.springframework.transaction.annotation.Transactional;

public class GlobalModelMongoRepository<T extends GlobalModel> extends SimpleMongoRepository<T, String> {
    public GlobalModelMongoRepository(MongoEntityInformation<T, String> metadata, MongoOperations mongoOperations) {
        super(metadata, mongoOperations);
    }

    @Transactional
    public <S extends T> S save(S entity) {
        if (entity.getID() != null) {
            // update
        } else {
            // save
        }
        return super.save(entity);
    }
}
