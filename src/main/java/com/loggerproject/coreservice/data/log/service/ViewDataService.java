package com.loggerproject.coreservice.data.log.service;

import com.loggerproject.coreservice.data.log.model.ViewData;
import com.loggerproject.coreservice.data.log.service.schema.SchemaDataSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ViewDataService {

    @Autowired
    SchemaDataSourceService schemaDataSourceService;

    public ViewData scrubAndValidate(ViewData viewData) throws Exception {
        Assert.notNull(viewData.getSchemaDataSource(), "SchemaDataSource cannot be null");
        return schemaDataSourceService.scrubAndValidate(viewData);
    }
}
