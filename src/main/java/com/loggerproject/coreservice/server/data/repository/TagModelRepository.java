package com.loggerproject.coreservice.server.data.repository;

import com.loggerproject.coreservice.server.data.document.tag.TagModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagModelRepository extends MongoRepository<TagModel, String> {
    List<TagModel> findByName(@Param("name") String name);
}
