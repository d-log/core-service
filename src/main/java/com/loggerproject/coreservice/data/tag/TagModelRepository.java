package com.loggerproject.coreservice.data.tag;

import com.loggerproject.coreservice.data.tag.model.TagModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagModelRepository extends MongoRepository<TagModel, String> {
}
