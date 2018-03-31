package com.loggerproject.coreservice.service.view;

import com.loggerproject.coreservice.data.document.view.ViewModel;
import com.loggerproject.coreservice.service.view.get.ViewModelGetService;
import org.everit.json.schema.Schema;
import org.everit.json.schema.SchemaException;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ViewModelUtilService {

    @Autowired
    ViewModelGetService viewModelGetService;

    public void validateJsonData(String viewModelID, String data) throws Exception {
        ViewModel model = viewModelGetService.validateAndFindOne(viewModelID);

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
            throw new Exception("ERROR document failed validation against custom json schema - " + e.getMessage() + "\nCUSTOM SCHEMA\n"
                    + model.getDataSchema().getJson()
                    + "\nJSON DATA\n"
                    + data);
        }
    }
}



