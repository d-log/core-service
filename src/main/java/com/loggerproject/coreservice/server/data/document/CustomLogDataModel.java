package com.loggerproject.coreservice.server.data.document;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.databind.JsonNode;
import com.loggerproject.coreservice.server.data.document.view.model.ValidateDataStatement;
import com.loggerproject.coreservice.global.server.document.model.GlobalModel;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "custom-log-logdata")
public class CustomLogDataModel extends GlobalModel {
    /**
     * ID instead of id because @RepositoryRestResource, otherwise it won't export this field
     */
    @Id
    String ID;
    String logDataClass;
    String dataSchema;
    ValidateDataStatement validateDataStatement; // additional validations of logdata

    // this allows to serialize actual json which is within this Model json
    // without it the Model.dataSchema must be in string format with escaped " quotations
    @JsonSetter("dataSchema")
    void setDataSchemaString(JsonNode data)
    {
        this.dataSchema = data.toString();
    }
}
