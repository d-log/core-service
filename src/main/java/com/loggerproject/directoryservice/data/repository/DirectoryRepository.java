package com.loggerproject.directoryservice.data.repository;

import com.loggerproject.directoryservice.data.model.DirectoryModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends CrudRepository<DirectoryModel, String> {
}
