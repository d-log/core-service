package com.loggerproject.coreservice.server.data.repository;

import com.loggerproject.coreservice.server.data.document.directory.DirectoryModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DirectoryModelRepository extends MongoRepository<DirectoryModel, String> {
    List<DirectoryModel> findByName(@Param("name") String name);
}
