package com.loggerproject.coreservice.service.data.view;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.service.data.view.get.ViewModelGetService;
import org.everit.json.schema.Schema;
import org.everit.json.schema.SchemaException;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ViewModelUtilService {

    @Autowired
    ViewModelGetService viewModelGetService;

    @Autowired
    ViewDataStatementService viewDataStatementService;

    public String scrubAndValidateDataSchemaJSON(String dataSchemaJSON) throws Exception {
        Assert.notNull(dataSchemaJSON, "dataSchemaJSON is null");

        // this validates the custom jsonSchema based on $schema value (exp. http://json-schema.org/draft-07/schema#)
        // throws SchemaException when custom json schema failed against $schema definition
        try {
            SchemaLoader.load(new JSONObject(dataSchemaJSON));
        } catch (Exception e) {
            throw new Exception("ERROR parsing json schema - " + e.getMessage());
        }

        // turn custom JSON schema to oneline
        return new JSONObject(dataSchemaJSON).toString();
    }

    public void validateJsonData(String data, String viewModelID) throws Exception {
        ViewModel model = viewModelGetService.validateAndFindOne(viewModelID);

        validateJsonAgainstSchema(data, model);
        viewDataStatementService.executeValidateDataStatementAgainstData(data, model.getValidateDataStatement());
    }

    private void validateJsonAgainstSchema(String data, ViewModel model) throws Exception {
        try {
            // this validates the jsonSchema based on $schema property
            Schema schema = SchemaLoader.load(new JSONObject(model.getDataSchemaJSON()));

            // validate json subject against custom json schema
            schema.validate(new JSONObject(data));
        }
        catch (SchemaException e) {
            // custom json schema failed $schema definition
            throw new Exception("ERROR database is in invalid state - view id: " + model.getID() + " contains an invalid custom json schema: " + model.getDataSchemaJSON());
        }
        catch (ValidationException e) {
            // json subject failed custom json schema
            throw new Exception("ERROR document failed validation against custom json schema - " + e.getMessage() + "\nCUSTOM SCHEMA\n"
                    + model.getDataSchemaJSON()
                    + "\nJSON DATA\n"
                    + data);
        }
    }
}



