package com.loggerproject.coreservice.server.data.repository;

import com.loggerproject.coreservice.server.data.document.customlogdata.CustomLogDataModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomLogDataModelRepository extends MongoRepository<CustomLogDataModel, String> {
    List<CustomLogDataModel> findByLogDataType(@Param("logDataClass") String name);
}
