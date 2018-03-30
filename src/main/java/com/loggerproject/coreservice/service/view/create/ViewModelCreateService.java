package com.loggerproject.coreservice.service.view.create;

import com.loggerproject.coreservice.data.document.view.DataSchema;
import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.data.repository.ViewModelRepositoryRestResource;
import com.loggerproject.coreservice.service.view.delete.ViewModelDeleteService;
import com.loggerproject.coreservice.service.view.get.ViewModelGetService;
import com.loggerproject.coreservice.service.view.update.ViewModelUpdateService;
import com.loggerproject.coreservice.service.viewtemplate.get.ViewTemplateModelGetService;
import com.loggerproject.microserviceglobalresource.server.service.create.GlobalServerCreateService;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ViewModelCreateService extends GlobalServerCreateService<ViewModel> {

    @Autowired
    ViewModelRepositoryRestResource viewModelRepositoryRestResource;

    @Autowired
    ViewTemplateModelGetService viewTemplateModelGetService;

    @Autowired
    public ViewModelCreateService(ViewModelRepositoryRestResource repository,
                                  @Lazy ViewModelCreateService globalServerCrearteService,
                                  @Lazy ViewModelDeleteService globalServerDeleteService,
                                  @Lazy ViewModelGetService globalServerGetService,
                                  @Lazy ViewModelUpdateService globalServerUpdateService) {
        super(repository, globalServerCrearteService, globalServerDeleteService, globalServerGetService, globalServerUpdateService);
    }

    public void scrubAndValidate(ViewModel model) throws Exception {
        model.setOtherViewTemplateIDs(model.getOtherViewTemplateIDs() != null ? model.getOtherViewTemplateIDs() : new HashSet<>());
        model.setDataSchema(model.getDataSchema() != null ? model.getDataSchema() : new DataSchema());
        model.getDataSchema().setJson(model.getDataSchema().getJson() != null ? model.getDataSchema().getJson() : "");

        Set<String> viewTemplateIDs = new HashSet<>(model.getOtherViewTemplateIDs());
        if (model.getDefaultViewTemplateID() != null) {
            viewTemplateIDs.add(model.getDefaultViewTemplateID());
        }
        viewTemplateModelGetService.validateIds(viewTemplateIDs);

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
                    throw new Exception("ERROR parsing json string of ViewModel.DataSchema.json - " + e.getMessage());
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
