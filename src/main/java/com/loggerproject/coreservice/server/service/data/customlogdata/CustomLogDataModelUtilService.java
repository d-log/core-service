package com.loggerproject.coreservice.server.service.data.customlogdata;

import com.loggerproject.coreservice.server.data.document.customlogdata.CustomLogDataModel;
import com.loggerproject.coreservice.server.service.data.customlogdata.get.CustomLogDataModelGetService;
import org.everit.json.schema.Schema;
import org.everit.json.schema.SchemaException;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class CustomLogDataModelUtilService {

    @Autowired
    CustomLogDataModelGetService customLogDataModelGetService;

    @Autowired
    LogDataStatementService logDataStatementService;

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
        CustomLogDataModel model = customLogDataModelGetService.validateAndFindOne(viewModelID);

        validateJsonAgainstSchema(data, model);
        logDataStatementService.executeValidateDataStatementAgainstData(data, model.getValidateDataStatement());
    }

    private void validateJsonAgainstSchema(String data, CustomLogDataModel model) throws Exception {
        try {
            // this validates the jsonSchema based on $schema property
            Schema schema = SchemaLoader.load(new JSONObject(model.getDataSchemaJSON()));

            // validate json subject against custom json schema
            schema.validate(new JSONObject(data));
        }
        catch (SchemaException e) {
            // custom json schema failed $schema definition
            throw new Exception("ERROR database is in invalid state - customlogdata id: " + model.getID() + " contains an invalid custom json schema: " + model.getDataSchemaJSON());
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



