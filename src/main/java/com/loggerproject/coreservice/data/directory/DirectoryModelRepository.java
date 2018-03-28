package com.loggerproject.coreservice.data.directory;

import com.loggerproject.coreservice.data.directory.model.DirectoryModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryModelRepository extends MongoRepository<DirectoryModel, String> {
}
