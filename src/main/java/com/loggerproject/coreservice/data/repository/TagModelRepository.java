package com.loggerproject.coreservice.data.repository;

import com.loggerproject.coreservice.data.model.tag.TagModel;
import org.springframework.stereotype.Repository;

@Repository
public interface TagModelRepository extends GlobalModelRepository<TagModel, String> {
}
