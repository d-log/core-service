package com.loggerproject.coreservice.service.log.create;

import com.loggerproject.coreservice.data.model.log.ViewData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ViewDataService {

    @Autowired
    SchemaDataSourceService schemaDataSourceService;

    public void scrubAndValidate(ViewData viewData) throws Exception {
        Assert.notNull(viewData.getSchemaDataSource(), "SchemaDataSource cannot be null");
        this.schemaDataSourceService.scrubAndValidate(viewData);
    }
}
