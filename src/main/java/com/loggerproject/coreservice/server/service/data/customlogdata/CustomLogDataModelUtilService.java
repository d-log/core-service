package com.loggerproject.coreservice.server.service.data.customlogdata;

import com.loggerproject.coreservice.server.data.document.customlogdata.CustomLogDataModel;
import com.loggerproject.coreservice.server.service.data.customlogdata.get.CustomLogDataModelGetService;
import org.apache.commons.lang3.StringUtils;
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

    public void validateLogDataTypeName(String logDataTypeName) throws Exception {
        Assert.notNull(logDataTypeName, "CustomLogDataModel.logDataType cannot be empty");

        if (!StringUtils.isAlpha(logDataTypeName)) {
            throw new Exception("CustomLogDataModel.logDataType: '" + logDataTypeName + "' should only contain alpha characters");
        }

        if (customLogDataModelGetService.findByLogDataType(logDataTypeName) != null) {
            throw new Exception("CustomLogDataModel.logDataType: '" + logDataTypeName + "' already exists");
        }

        Integer length = logDataTypeName.length();
        if (length < 8) {
            throw new Exception("CustomLogDataModel.logDataType: '" + logDataTypeName + "' should contain 8 or more alpha characters");
        }

        String lastSeven = logDataTypeName.substring(logDataTypeName.length() - 7);
        if (!lastSeven.equals("LogData")) {
            throw new Exception("CustomLogDataModel.logDataType: '" + logDataTypeName + "' should have 'LogData' as the last 7 characters");
        }

        if (!Character.isUpperCase(logDataTypeName.charAt(0))) {
            throw new Exception("CustomLogDataModel.logDataType: '" + logDataTypeName + "' should start with an upper case character");
        }
    }

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

    public void validateDataByLogDataType(String data, String logDataType) throws Exception {
        CustomLogDataModel model = customLogDataModelGetService.validateAndFindByLogDataType(logDataType);
        validateDataByModel(data, model);
    }

    public void validateDataByID(String data, String customLogDataID) throws Exception {
        CustomLogDataModel model = customLogDataModelGetService.validateAndFindOne(customLogDataID);
        validateDataByModel(data, model);
    }

    private void validateDataByModel(String data, CustomLogDataModel model) throws Exception {
        validateDataAgainstSchema(data, model);
        logDataStatementService.executeValidateDataStatementAgainstData(data, model.getValidateDataStatement());
    }

    private void validateDataAgainstSchema(String data, CustomLogDataModel model) throws Exception {
        try {
            // this validates the jsonSchema based on $schema property
            Schema schema = SchemaLoader.load(new JSONObject(model.getDataSchemaJSON()));

            // validate json subject against custom json schema
            schema.validate(new JSONObject(data));
        } catch (SchemaException e) {
            // custom json schema failed $schema definition
            throw new Exception("ERROR database is in invalid state - customlogdata id: " + model.getID() + " contains an invalid custom json schema: " + model.getDataSchemaJSON());
        } catch (ValidationException e) {
            // json subject failed custom json schema
            throw new Exception("ERROR document failed validation against custom json schema - " + e.getMessage() + "\nCUSTOM SCHEMA\n"
                    + model.getDataSchemaJSON()
                    + "\nJSON DATA\n"
                    + data);
        }
    }
}



