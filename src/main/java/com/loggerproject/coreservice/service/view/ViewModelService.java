package com.loggerproject.coreservice.service.view;

import com.loggerproject.coreservice.data.model.view.DataSchema;
import com.loggerproject.coreservice.data.model.view.ViewModel;
import com.loggerproject.coreservice.data.repository.ViewModelRepositoryRestResource;
import com.loggerproject.microserviceglobalresource.server.service.GlobalServerService;
import org.everit.json.schema.Schema;
import org.everit.json.schema.SchemaException;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("unchecked")
public class ViewModelService extends GlobalServerService<ViewModel> {

    @Autowired
    public ViewModelService(ViewModelRepositoryRestResource repository) {
        super(repository);
    }

    public void scrubAndValidate(ViewModel model) throws Exception {
        this.scrubAndValidateDataSchema(model);
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

    public void validateJsonData(String viewModelID, String data) throws Exception {
        ViewModel model = repository.findOne(viewModelID);
        if (model == null) throw new Exception("ERROR cannot find view model with id: '" + viewModelID + "'");

        try {
            // this validates the jsonSchema based on $schema property
            Schema schema = SchemaLoader.load(new JSONObject(model.getDataSchema().getJson()));

            // validate json subject against custom json schema
            schema.validate(new JSONObject(data));
        }
        catch (SchemaException e) {
            // custom json schema failed $schema definition
            throw new Exception("ERROR database is in invalid state - view id: " + viewModelID + " contains an invalid custom json schema: " + model.getDataSchema().getJson());
        }
        catch (ValidationException e) {
            // json subject failed custom json schema
            throw new Exception("ERROR model failed validation against custom json schema - " + e.getMessage() + "\nCUSTOM SCHEMA\n"
                    + model.getDataSchema().getJson()
                    + "\nJSON DATA\n"
                    + data);
        }
    }
}



