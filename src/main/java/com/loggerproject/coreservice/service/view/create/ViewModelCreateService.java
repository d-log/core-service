package com.loggerproject.coreservice.service.view.create;

import com.loggerproject.coreservice.data.document.view.DataSchema;
import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.repository.ViewModelRepositoryRestResource;
import com.loggerproject.coreservice.service.view.delete.ViewModelDeleteService;
import com.loggerproject.coreservice.service.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.view.update.ViewModelUpdateService;
import com.loggerproject.microserviceglobalresource.server.service.create.GlobalServerCreateService;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class ViewModelCreateService extends GlobalServerCreateService<ViewModel> {

    @Autowired
    ViewModelRepositoryRestResource viewModelRepositoryRestResource;

    @Autowired
    public ViewModelCreateService(ViewModelRepositoryRestResource repository,
                                  @Lazy ViewModelDeleteService globalServerDeleteService,
                                  @Lazy ViewModelGetService globalServerGetService,
                                  @Lazy ViewModelUpdateService globalServerUpdateService) {
        super(repository, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public void scrubAndValidate(ViewModel model) throws Exception {
        scrubAndValidateDataSchema(model);

    }

    private void scrubAndValidateDataSchema(ViewModel model) throws Exception {
        DataSchema dataSchema = model.getDataSchema();

        if (dataSchema != null) {
            String dataSchemaJSON = dataSchema.getJson();
            if (dataSchemaJSON != null) {
                // this validates the custom jsonSchema based on $schema value (exp. http://json-schema.org/draft-07/schema#)
                // throws SchemaException when custom json schema failed against $schema definition
                try {
                    SchemaLoader.load(new JSONObject(dataSchemaJSON));
                } catch (Exception e) {
                    throw new Exception("ERROR parsing json dataSchema - " + e.getMessage());
                }

                // turn custom JSON schema to oneline
                dataSchema.setJson(new JSONObject(dataSchemaJSON).toString());
            }
        }
    }

    @Override
    protected void beforeSave(ViewModel model) throws Exception {
        scrubAndValidate(model);
        super.beforeSave(model);
    }
}
