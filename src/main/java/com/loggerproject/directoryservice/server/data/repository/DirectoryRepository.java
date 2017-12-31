package com.loggerproject.directoryservice.server.data.repository;

import com.loggerproject.directoryservice.server.data.model.DirectoryModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends CrudRepository<DirectoryModel, String> {
}
