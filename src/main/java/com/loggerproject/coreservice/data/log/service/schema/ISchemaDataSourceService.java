package com.loggerproject.coreservice.data.log.service.schema;

import com.loggerproject.coreservice.data.log.model.ViewData;

public interface ISchemaDataSourceService {
    ViewData scrubAndValidate(ViewData viewData) throws Exception;
}
