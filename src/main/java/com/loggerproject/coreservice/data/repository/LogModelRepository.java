package com.loggerproject.coreservice.data.repository;

import com.loggerproject.coreservice.data.model.log.LogModel;
import org.springframework.stereotype.Repository;

@Repository
public interface LogModelRepository extends GlobalModelRepository<LogModel, String> {
}
